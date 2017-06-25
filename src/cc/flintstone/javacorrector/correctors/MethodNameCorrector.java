package cc.flintstone.javacorrector.correctors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import bluej.extensions.BClass;
import bluej.extensions.PackageNotFoundException;
import bluej.extensions.ProjectNotOpenException;

public class MethodNameCorrector implements Corrector {
	
	public static final MethodNameCorrector INSTANCE = new MethodNameCorrector();
	public Set<String> methodNameNotCorrect = null;

	@Override
	public boolean correct(List<BClass> classe) {
			
			for (BClass bClass : classe) {
				FileInputStream fileInputStream = null;
				try {
					fileInputStream = new FileInputStream(bClass.getClassFile());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(fileInputStream!= null){
			        CompilationUnit cu = JavaParser.parse(fileInputStream);
	
			        MethodVisitor mVisitor = new MethodVisitor();]
			        mVisitor.className = bClass.getName();
			        mVisitor.visit(cu, null);
		        }
			}
			if(methodNameNotCorrect!=null){
				return false;
			}else{
				return true;
			}
	    }

	    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
	    	private String className = null;
	    	
	        @Override
	        public void visit(MethodDeclaration n, Void arg) {
	            if(INSTANCE.isFirstLetterUpperCase(n.getNameAsString())!=1){
	            	INSTANCE.methodNameNotCorrect.add(n.getNameAsString()+","+className);
	            }
	            super.visit(n, arg);
	        }
	    }
		public int isFirstLetterUpperCase(String word){
			word = word.trim();
			
			if(word == null||word.length()<=0) return -1;
			
			if(word.substring(0, 1).toCharArray()[0]>='A'&& word.substring(0, 1).toCharArray()[0]<='Z'){
				return 1;
			} else {
				return 0;
			}
		}
		
}
