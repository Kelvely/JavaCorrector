package cc.flintstone.javacorrector.correctors;

import java.util.List;

import bluej.extensions.BClass;

public class ClassNameCorrector implements Corrector {
	
	public static final ClassNameCorrector INSTANCE = new ClassNameCorrector();

	@Override
	public boolean correct(List<BClass> classes) {
		for (BClass bClass : classes) {

			FileInputStream in = new FileInputStream(bClass.getClassFile());

	        CompilationUnit cu = JavaParser.parse(in);

	        String ClassName = new MethodVisitor().visit(cu, null);
	        
	        if(isFirstLetterUpperCase(ClassName)) return true;
	        else return false;
		}
	}
	
	private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public String visit(MethodDeclaration n, Void arg) {
            super.visit(n, arg);
            return n.getName();
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
