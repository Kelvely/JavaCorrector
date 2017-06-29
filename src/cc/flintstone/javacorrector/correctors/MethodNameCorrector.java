package cc.flintstone.javacorrector.correctors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.Position;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import bluej.extensions.BClass;
import bluej.extensions.PackageNotFoundException;
import bluej.extensions.ProjectNotOpenException;
import cc.flintstone.javacorrector.util.EditorDemonstrator;
import cc.flintstone.javacorrector.util.SelectionConverter;
import cc.flintstone.javacorrector.util.SelectionConverter.JavaParserSelection;

public class MethodNameCorrector implements Corrector {
	
	public static final MethodNameCorrector INSTANCE = new MethodNameCorrector();

	@Override
	public boolean correct(BClass bClass) {
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
		
		ImproperlyNamedMethodsGetter imGetter = new ImproperlyNamedMethodsGetter();
		
		imGetter.visit(cUnit, null);
		
		List<MethodDeclaration> improperlyNamedMethods = imGetter.getImproperlyNamedMethods();
		
		if(!improperlyNamedMethods.isEmpty()) {
			MethodDeclaration method = improperlyNamedMethods.get(0);
			
			Position start = method.getName().getBegin().get();
			Position end = method.getName().getEnd().get();
			try {
				EditorDemonstrator.preachCode(bClass.getEditor(), 
						SelectionConverter.toBlueJ(new JavaParserSelection(start, end)), 
						"Improper method naming \"" + method.getNameAsString() + "\" - " + 
						"Method name should be in camelcase");
			} catch (ProjectNotOpenException | PackageNotFoundException e) {
			}
			return true;
		} else {
			return false;
		}
    }
	
	private final static class ImproperlyNamedMethodsGetter extends VoidVisitorAdapter<Void> {
		
		private final List<MethodDeclaration> improperlyNamedMethods = new ArrayList<>();
		
		public List<MethodDeclaration> getImproperlyNamedMethods() {
			List<MethodDeclaration> clonedList = new ArrayList<>();
			clonedList.addAll(improperlyNamedMethods);
			return clonedList;
		}
		
		@Override
		public void visit(MethodDeclaration n, Void arg) {
			if(!isNameProper(n.getNameAsString())) {
				improperlyNamedMethods.add(n);
			}
			
			super.visit(n, arg);
		}
		
		@Override
		public void visit(CompilationUnit n, Void arg) {
			improperlyNamedMethods.clear();
			
			super.visit(n, arg);
		}
	}
	
	private static boolean isNameProper(String className) {
		return Character.isLowerCase(className.charAt(0));
	}
		
}
