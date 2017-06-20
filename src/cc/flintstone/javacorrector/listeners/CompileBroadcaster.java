package cc.flintstone.javacorrector.listeners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bluej.extensions.BlueJ;
import bluej.extensions.event.CompileEvent;
import bluej.extensions.event.CompileListener;
import cc.flintstone.javacorrector.correctors.Corrector;

public class CompileBroadcaster implements CompileListener {
	
	private final BlueJ blueJ;
	private final List<Corrector> correctors = new ArrayList<>();
	
	public CompileBroadcaster(BlueJ blueJ, Collection<Corrector> correctors) {
		this.blueJ = blueJ;
		this.correctors.addAll(correctors);
	}

	@Override
	public void compileError(CompileEvent compileEvent) {}

	@Override
	public void compileFailed(CompileEvent compileEvent) {}

	@Override
	public void compileStarted(CompileEvent compileEvent) {}

	@Override
	public void compileSucceeded(CompileEvent compileEvent) {
		for (Corrector corrector : correctors) {
			if(corrector.correct(compileEvent, blueJ)) break;
		}
	}

	@Override
	public void compileWarning(CompileEvent compileEvent) {}

}
