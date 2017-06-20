package cc.flintstone.javacorrector.correctors;

import bluej.extensions.BlueJ;
import bluej.extensions.event.CompileEvent;

public interface Corrector {
	
	public boolean correct(CompileEvent compileEvent, BlueJ blueJ);

}
