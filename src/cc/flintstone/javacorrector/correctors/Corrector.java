package cc.flintstone.javacorrector.correctors;

import bluej.extensions.event.CompileEvent;

public interface Corrector {
	
	public void correct(CompileEvent compileEvent);

}
