package cc.flintstone.javacorrector.correctors;

import java.util.List;

import bluej.extensions.BClass;
import bluej.extensions.BlueJ;

public interface Corrector {
	
	public boolean correct(List<BClass> classes, BlueJ blueJ);

}
