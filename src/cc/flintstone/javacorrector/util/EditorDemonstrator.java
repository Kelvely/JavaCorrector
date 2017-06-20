package cc.flintstone.javacorrector.util;

import bluej.extensions.editor.Editor;
import bluej.extensions.editor.TextLocation;

public class EditorDemonstrator {
	
	public static void showMessage(Editor editor, String message) {
		if(!editor.isVisible()) editor.setVisible(true);
		editor.showMessage(message);
	}
	
	public static void highlight(Editor editor, TextLocation startLoc, TextLocation endLoc) {
		if(!editor.isVisible()) editor.setVisible(true);
		editor.setSelection(startLoc, endLoc);
	}
	
	/*
	 * Preach lol :DDDD
	 */
	public static void preachCode(Editor editor, TextLocation startLoc, TextLocation endLoc, String message) {
		if(!editor.isVisible()) editor.setVisible(true);
		editor.setSelection(startLoc, endLoc);
		editor.showMessage(message);
	}

}
