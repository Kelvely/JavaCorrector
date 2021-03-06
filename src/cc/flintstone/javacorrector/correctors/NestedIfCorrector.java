package cc.flintstone.javacorrector.correctors;

import java.io.IOException;
import java.util.Optional;

import com.github.javaparser.JavaParser;
import com.github.javaparser.Position;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import bluej.extensions.BClass;
import bluej.extensions.PackageNotFoundException;
import bluej.extensions.ProjectNotOpenException;
import cc.flintstone.javacorrector.util.EditorDemonstrator;
import cc.flintstone.javacorrector.util.SelectionConverter;
import cc.flintstone.javacorrector.util.SelectionConverter.JavaParserSelection;

public class NestedIfCorrector implements Corrector {
	
	public static final NestedIfCorrector INSTANCE = new NestedIfCorrector();

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
		
		NestedIfChecker checker = new NestedIfChecker(3);
		
		checker.visit(cUnit, null);
		
		IfStmt overnestedIfStmt = checker.getOvernestedIfStmt();
		
		if(overnestedIfStmt != null) {
			
			Position start = overnestedIfStmt.getBegin().get();
			Position end = overnestedIfStmt.getEnd().get();
			try {
				EditorDemonstrator.preachCode(bClass.getEditor(), 
						SelectionConverter.toBlueJ(new JavaParserSelection(start, end)),
						"Too deep nested if - If statement should not be nested for more than 3 times");
			} catch (ProjectNotOpenException | PackageNotFoundException e) {
			}
			return true;
		} else {
			return false;
		}
	}
	
	private final static class NestedIfChecker extends VoidVisitorAdapter<Void> {
		
		private IfStmt overnestedIfStmt;
		private final int nestLeft;
		
		public NestedIfChecker(int nestLeft) {
			this.nestLeft = nestLeft;
		}
		
		public IfStmt getOvernestedIfStmt() {
			return overnestedIfStmt;
		}
		
		@Override
		public void visit(IfStmt n, Void arg) {
			if(overnestedIfStmt != null) return;
			
			final int nextNestLeft = nestLeft - 1;
			
			if(nextNestLeft < 0) {
				overnestedIfStmt = n;
				return;
			} else {
				NestedIfChecker checker = new NestedIfChecker(nextNestLeft);
				
				Statement thenStmt = n.getThenStmt();
				if(thenStmt instanceof BlockStmt) {
					checker.visit((BlockStmt) thenStmt, arg);
					if(checker.overnestedIfStmt != null) {
						overnestedIfStmt = checker.overnestedIfStmt;
						return;
					}
				}
				
				Optional<Statement> elseStmtOp = n.getElseStmt();
				if(elseStmtOp.isPresent()) {
					Statement elseStmt = elseStmtOp.get();
					if(elseStmt instanceof BlockStmt) {
						checker.visit((BlockStmt) elseStmt, arg);
						overnestedIfStmt = checker.overnestedIfStmt;
					}
				}
			}
			
		}
		
		@Override
		public void visit(CompilationUnit n, Void arg) {
			overnestedIfStmt = null;
			
			super.visit(n, arg);
		}
	}

}
