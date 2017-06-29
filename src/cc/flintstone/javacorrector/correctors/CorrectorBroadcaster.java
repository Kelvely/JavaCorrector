package cc.flintstone.javacorrector.correctors;

import java.util.Collection;

import bluej.extensions.BClass;

public class CorrectorBroadcaster {
	
	public static boolean broadcast(Collection<Corrector> correctors, BClass bClass) {
		for (Corrector corrector : correctors) {
			if(corrector.correct(bClass)) return true;
		}
		return false;
	}

}
