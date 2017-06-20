package cc.flintstone.javacorrector.correctors.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import bluej.extensions.BClass;
import bluej.extensions.BPackage;
import bluej.extensions.BProject;
import bluej.extensions.BlueJ;
import bluej.extensions.PackageNotFoundException;
import bluej.extensions.ProjectNotOpenException;


/*
 * This utility class is written in reason of the .... Hey you can't 
 * ... No!!! (Then being towed away by Kent University guards)
 */
public class Pathfinder {
	
	public static Map<File, BClass> findAllClasses(BlueJ blueJ, File[] files) {
		Map<File, BClass> classes = new HashMap<>();
		
		BProject[] projects = blueJ.getOpenProjects();
		
		for (BProject project : projects) {
			classes.putAll(findAllClasses(project, files));
		}
		
		return classes;
	}
	
	/**
	 * Find all classes in the project :D
	 * @param project The project.
	 * @param files Java files.
	 * @return All classes found.
	 */
	public static Map<File, BClass> findAllClasses(BProject project, File[] files) {
		//List<BClass> classes = new ArrayList<>();
		Map<File, BClass> classes = new HashMap<>();
		
		BPackage[] packages;
		try {
			packages = project.getPackages();
		} catch (ProjectNotOpenException e) {
			return classes;
		}
		
		for (BPackage pkg : packages) {
			//classes.addAll(findAllClasses(pkg, files));
			classes.putAll(findAllClasses(pkg, files));
		}
		
		return classes;
	}
	
	public static Map<File, BClass> findAllClasses(BPackage pkg, File[] files) {
		//List<BClass> classes = new ArrayList<>();
		Map<File, BClass> classes = new HashMap<>();
		
		BClass[] rawClasses;
		try {
			rawClasses = pkg.getClasses();
		} catch (ProjectNotOpenException | PackageNotFoundException e) {
			return classes;
		}
		
		for (BClass bClass : rawClasses) {
			File fileFound = getCorrespondingFile(bClass, files);
			if(fileFound != null) {
				classes.put(fileFound, bClass);
			}
		}
		
		return classes;
	}
	
	private static File getCorrespondingFile(BClass bClass, File[] files) {
		for (File file : files) {
			try {
				if(bClass.getClassFile().equals(file)) return file;
			} catch (ProjectNotOpenException | PackageNotFoundException e) {
				return null;
			}
		}
		return null;
	}

}
