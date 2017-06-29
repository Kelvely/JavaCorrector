package cc.flintstone.javacorrector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
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
	
	static {
		try {
			Class.forName("com.github.javaparser.JavaParser");
		} catch (ClassNotFoundException ex) {
			URL javaparserUrl = JavaCorrector.class.getResource("javaparser-core-3.2.9-SNAPSHOT.jar");
			try {
				Method urlAdder = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
				boolean accessibility = urlAdder.isAccessible();
			
			
				if(!accessibility) {
					urlAdder.setAccessible(true);
				}
				
				URLClassLoader classloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
				urlAdder.invoke(classloader, javaparserUrl);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | 
					IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
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
