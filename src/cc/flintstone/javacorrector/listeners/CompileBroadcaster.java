package cc.flintstone.javacorrector.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import bluej.extensions.BClass;
import bluej.extensions.BlueJ;
import bluej.extensions.event.CompileEvent;
import bluej.extensions.event.CompileListener;
import cc.flintstone.javacorrector.correctors.Corrector;
import cc.flintstone.javacorrector.util.Pathfinder;

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
		File[] files = compileEvent.getFiles();
		Map<File, BClass> rawClasses = Pathfinder.findAllClasses(blueJ, files);
		
		List<BClass> classes = new ArrayList<>();
		
		for (File file : files) {
			BClass bClass = rawClasses.get(file);
			if(bClass != null) {
				classes.add(bClass);
			}
		}
		
		for (Corrector corrector : correctors) {
			if(corrector.correct(classes, blueJ)) break;
		}
	}

	@Override
	public void compileWarning(CompileEvent compileEvent) {}

}
