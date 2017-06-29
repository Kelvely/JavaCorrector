package cc.flintstone.javacorrector.util;

import com.github.javaparser.Position;

import bluej.extensions.editor.TextLocation;

public class SelectionConverter {
	
	public static BlueJSelection toBlueJ(JavaParserSelection selection) {
		return new BlueJSelection(
				new TextLocation(selection.from.line - 1, selection.from.column - 1), 
				new TextLocation(selection.to.line - 1, selection.to.column)
				);
	}
	
	public static JavaParserSelection toJavaParser(BlueJSelection selection) {
		return new JavaParserSelection(
				new Position(selection.from.getLine() + 1, selection.from.getColumn() + 1),
				new Position(selection.to.getLine() + 1, selection.to.getColumn()));
	}
	
	public static final class BlueJSelection {
		
		public final TextLocation from;
		public final TextLocation to;
		
		public BlueJSelection(TextLocation from, TextLocation to) {
			this.from = from;
			this.to = to;
		}
		
	}
	
	public static final class JavaParserSelection {
		
		public final Position from;
		public final Position to;
		
		public JavaParserSelection(Position from, Position to) {
			this.from = from;
			this.to = to;
		}
		
	}

}
