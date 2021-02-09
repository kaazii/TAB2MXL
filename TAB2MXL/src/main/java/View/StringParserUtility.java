package View;

import java.util.ArrayList;

import TAB2MXL.Measure;
import TAB2MXL.Note;

public final class StringParserUtility {
	
	public ArrayList<Measure> measureList = new ArrayList<Measure>();
	
	public static String stringParse (String input) {
		String lines[] = input.split("\\r?\\n");
		
		//for error testing
		for (int i = 0; i < lines.length; i++) {
			System.out.println(lines[i]);
		}
		System.out.println("numLines: " + lines.length);
		
		//basic checks
		if (lines[0].toUpperCase().startsWith("E")) {
			return "This is a Guitar tab.";
		}
		else if (lines[0].toUpperCase().startsWith("C")) {
			return "This is a Drum tab.";
		}
		else if (lines[0].toUpperCase().startsWith("G")) {
			return "This is a Bass tab.";
		}
		return "Could not recognize the tablature.";
		//Measure measure = new Measure();
	}
	
	public Note getNote(int row, int column) {
		//todo
		return new Note('a', 1, 1); 
	}
	
	public ArrayList<Measure> getMeasureList(){
		return this.measureList;
	}
}