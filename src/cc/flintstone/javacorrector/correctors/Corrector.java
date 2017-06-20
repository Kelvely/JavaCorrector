package cc.flintstone.javacorrector.correctors;

import java.util.List;

import bluej.extensions.BClass;

public interface Corrector {
	
	public boolean correct(List<BClass> classes);

}
