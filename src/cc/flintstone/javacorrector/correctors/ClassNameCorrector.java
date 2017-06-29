package cc.flintstone.javacorrector.correctors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.Position;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import bluej.extensions.BClass;
import bluej.extensions.PackageNotFoundException;
import bluej.extensions.ProjectNotOpenException;
import bluej.extensions.editor.TextLocation;
import cc.flintstone.javacorrector.util.EditorDemonstrator;

public class ClassNameCorrector implements Corrector {
	
	public static final ClassNameCorrector INSTANCE = new ClassNameCorrector();

	@Override
	public boolean correct(List<BClass> classes) {
		for (BClass bClass : classes) {
			CompilationUnit cUnit;
			try {
				cUnit = JavaParser.parse(bClass.getJavaFile());
			} catch (IOException ex) {
				try {
					EditorDemonstrator.showMessage(bClass.getEditor(), "Failed to load the java file!");
				} catch (ProjectNotOpenException | PackageNotFoundException e) {
					return true;
				}
				return true;
			} catch (ProjectNotOpenException | PackageNotFoundException e) {
				return true;
			}
			
			ImproperlyNamedClassesGetter icGetter = new ImproperlyNamedClassesGetter();
			
			icGetter.visit(cUnit, null);
			
			List<ClassOrInterfaceDeclaration> improperlyNamedClasses = icGetter.getImproperlyNamedClasses();
			
			if(!improperlyNamedClasses.isEmpty()) {
				ClassOrInterfaceDeclaration clazz = improperlyNamedClasses.get(0);
				
				Position start = clazz.getName().getBegin().get();
				Position end = clazz.getName().getEnd().get();
				try {
					EditorDemonstrator.preachCode(bClass.getEditor(), 
							new TextLocation(start.line, start.column), 
							new TextLocation(end.line, end.column), 
							"Improper class or interface naming \"" + clazz.getNameAsString() + "\" - " + 
							"Class or interface name should be in titlecase");
				} catch (ProjectNotOpenException | PackageNotFoundException e) {
				}
				return true;
			}
			
		}
		
		return false;
    }
	
	private final static class ImproperlyNamedClassesGetter extends VoidVisitorAdapter<Void> {
		
		private final List<ClassOrInterfaceDeclaration> improperlyNamedClasses = new ArrayList<>();
		
		public List<ClassOrInterfaceDeclaration> getImproperlyNamedClasses() {
			List<ClassOrInterfaceDeclaration> clonedList = new ArrayList<>();
			clonedList.addAll(improperlyNamedClasses);
			return clonedList;
		}
		
		@Override
		public void visit(ClassOrInterfaceDeclaration n, Void arg) {
			if(!isNameProper(n.getNameAsString())) {
				improperlyNamedClasses.add(n);
			}
			
			super.visit(n, arg);
		}
		
		@Override
		public void visit(CompilationUnit n, Void arg) {
			improperlyNamedClasses.clear();
			
			super.visit(n, arg);
		}
	}
	
	private static boolean isNameProper(String className) {
		return Character.isUpperCase(className.charAt(0));
	}
		
}
