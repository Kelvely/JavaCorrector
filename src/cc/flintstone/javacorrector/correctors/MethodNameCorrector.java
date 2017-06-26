package cc.flintstone.javacorrector.correctors;

import java.util.List;


import bluej.extensions.BClass;

public class MethodNameCorrector implements Corrector {
	
	public static final MethodNameCorrector INSTANCE = new MethodNameCorrector();

	@Override
	public boolean correct(List<BClass> classes) {
		// TODO
		return false;
	}
		
}
