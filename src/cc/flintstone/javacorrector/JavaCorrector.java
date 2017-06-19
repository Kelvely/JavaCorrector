package cc.flintstone.javacorrector;

import java.util.ArrayList;
import java.util.List;

import com.sun.corba.se.impl.util.Version;

import bluej.extensions.BlueJ;
import bluej.extensions.Extension;
import cc.flintstone.javacorrector.correctors.Corrector;

public class JavaCorrector extends Extension {
	
	private BlueJ blueJ;
	private final List<Corrector> correctors = new ArrayList<>();
	
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
