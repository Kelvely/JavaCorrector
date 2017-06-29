package cc.flintstone.javacorrector.correctors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.Position;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import bluej.extensions.BClass;
import bluej.extensions.PackageNotFoundException;
import bluej.extensions.ProjectNotOpenException;
import bluej.extensions.editor.TextLocation;
import cc.flintstone.javacorrector.util.EditorDemonstrator;

public class FieldNameCorrector implements Corrector {
	
	public static final FieldNameCorrector INSTANCE = new FieldNameCorrector();

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
		
		ImproperlyNamedFieldGetter ivGetter = new ImproperlyNamedFieldGetter();
		
		ivGetter.visit(cUnit, null);
		
		List<VariableDeclarator> improperlyNamedVariables = ivGetter.getImproperlyNamedVariables();
		
		if(improperlyNamedVariables.size() > 0) {
			VariableDeclarator var = improperlyNamedVariables.get(0);
			
			Position start = var.getName().getBegin().get();
			Position end = var.getName().getEnd().get();
			try {
				EditorDemonstrator.preachCode(bClass.getEditor(), 
						new TextLocation(start.line, start.column), 
						new TextLocation(end.line, end.column), 
						"Improper field naming \"" + var.getNameAsString() + "\" - " + 
						"Normal field name should be in camelcase, and unchanged field name should be capitalized");
			} catch (ProjectNotOpenException | PackageNotFoundException e) {
			}
			return true;
		} else {
			return false;
		}
    }
	
	private final static class ImproperlyNamedFieldGetter extends VoidVisitorAdapter<Void> {
		
		private final List<VariableDeclarator> improperlyNamedVariables = new ArrayList<>();
		
		public List<VariableDeclarator> getImproperlyNamedVariables() {
			List<VariableDeclarator> clonedList = new ArrayList<>();
			clonedList.addAll(improperlyNamedVariables);
			return clonedList;
		}
		
		@Override
		public void visit(FieldDeclaration n, Void arg) {
			List<VariableDeclarator> variables = n.getVariables();
			
			for (VariableDeclarator variable : variables) {
				if(!isNameProper(variable.getNameAsString())) {
					improperlyNamedVariables.add(variable);
				}
			}
		}
		
		@Override
		public void visit(CompilationUnit n, Void arg) {
			improperlyNamedVariables.clear();
			
			super.visit(n, arg);
		}
	}
	
	private static boolean isNameProper(String fieldName) {
		if(Character.isUpperCase(fieldName.charAt(0))) {
			return fieldName.toUpperCase().equals(fieldName);
		} else return true;
	}

}
