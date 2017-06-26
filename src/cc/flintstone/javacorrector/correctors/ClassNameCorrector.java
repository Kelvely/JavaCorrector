package cc.flintstone.javacorrector.correctors;

import java.util.List;

import bluej.extensions.BClass;

public class ClassNameCorrector implements Corrector {
	
	public static final ClassNameCorrector INSTANCE = new ClassNameCorrector();

	@Override
	public boolean correct(List<BClass> classe) {
		// TODO
		return false;
    }
		
}
