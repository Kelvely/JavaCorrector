package cc.flintstone.javacorrector.correctors;

import java.util.List;
import java.util.Set;

import bluej.extensions.BClass;

public class ClassNameCorrector implements Corrector {
	
	public static final ClassNameCorrector INSTANCE = new ClassNameCorrector();
	public Set<String> ClassNameNotCorrect = null;

	@Override
	public boolean correct(List<BClass> classe) {
			
		for (BClass bClass : classe) {
            if(isFirstLetterUpperCase(bClass.getName())!=1){
            	INSTANCE.ClassNameNotCorrect.add(bClass.getName());
            }
		}
		if(ClassNameNotCorrect!=null){
			return false;
		}else{
			return true;
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
