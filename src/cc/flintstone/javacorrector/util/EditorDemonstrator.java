package cc.flintstone.javacorrector.util;

import bluej.extensions.editor.Editor;
import cc.flintstone.javacorrector.util.SelectionConverter.BlueJSelection;

public class EditorDemonstrator {
	
	public static void showMessage(Editor editor, String message) {
		if(!editor.isVisible()) editor.setVisible(true);
		editor.showMessage(message);
	}
	
	public static void highlight(Editor editor, BlueJSelection selection) {
		if(!editor.isVisible()) editor.setVisible(true);
		editor.setSelection(selection.from, selection.to);
	}
	
	/*
	 * Preach lol :DDDD
	 */
	public static void preachCode(Editor editor, BlueJSelection selection, String message) {
		if(!editor.isVisible()) editor.setVisible(true);
		editor.setSelection(selection.from, selection.to);
		editor.showMessage(message);
	}

}
