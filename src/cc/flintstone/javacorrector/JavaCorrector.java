package cc.flintstone.javacorrector;

import java.util.ArrayList;
import java.util.List;

import bluej.extensions.BlueJ;
import bluej.extensions.Extension;
import cc.flintstone.javacorrector.correctors.ClassNameCorrector;
import cc.flintstone.javacorrector.correctors.Corrector;
import cc.flintstone.javacorrector.correctors.FieldNameCorrector;
import cc.flintstone.javacorrector.correctors.MethodNameCorrector;
import cc.flintstone.javacorrector.correctors.NestedIfCorrector;
import cc.flintstone.javacorrector.correctors.VariableNameCorrector;
import cc.flintstone.javacorrector.listeners.CompileBroadcaster;

public class JavaCorrector extends Extension {
	
	private BlueJ blueJ;
	private CompileBroadcaster broadcaster;
	
	private static final String NAME = "Java Corrector";
	private static final String VERSION = "0.1A";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getVersion() {
		return VERSION;
	}

	@Override
	public boolean isCompatible() {
		return true;
	}

	@Override
	public void startup(BlueJ blueJ) {
		this.blueJ = blueJ;
		
		List<Corrector> correctors = new ArrayList<>();
		correctors.add(ClassNameCorrector.INSTANCE);
		correctors.add(FieldNameCorrector.INSTANCE);
		correctors.add(MethodNameCorrector.INSTANCE);
		correctors.add(NestedIfCorrector.INSTANCE);
		correctors.add(VariableNameCorrector.INSTANCE);
		broadcaster = new CompileBroadcaster(blueJ, correctors);
		
		blueJ.addCompileListener(broadcaster);
	}
	
	public BlueJ getBlueJ() {
		return blueJ;
	}
	
	/*
	 * Make it become a main class to make compiler output MANIFEST.MF correctly :P
	 */
	public static void main(String[] args) {
		System.exit(1);
	}

}
