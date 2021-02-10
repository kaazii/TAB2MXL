package View;

import java.util.ArrayList;
import java.util.Arrays;

import TAB2MXL.Measure;
import TAB2MXL.Note;
import TAB2MXL.NoteUtility;

public final class StringParserUtility {

	public ArrayList<Measure> measureList = new ArrayList<Measure>();

	public static String stringParse(String input) {
		String lines[] = input.split("\\r?\\n");

		return "";
		// Measure measure = new Measure();
	}

	public static String checkTabType(String input) {
		// split the input into different lines
		String lines[] = input.split("\\r?\\n");
		String splitLines [][] = new String[lines.length][];
		
		for (int i = 0; i < lines.length; i++) {
			String currLine[] = lines[i].split("\\|");
			splitLines[i] = currLine;
			System.out.println(splitLines[i][0]);
		}

		 /* // for error testing for (int i = 0; i < lines.length; i++) {
		 * System.out.println(lines[i]); }
		 * 
		 * // for error testing System.out.println("numLines: " + lines.length);
		 * 
		 * 
		 * // basic checks if (lines[0].toUpperCase().startsWith("E")) { return
		 * "This is a Guitar tab."; } else if (lines[0].toUpperCase().startsWith("C")) {
		 * return "This is a Drum tab."; } else if
		 * (lines[0].toUpperCase().startsWith("G")) { return "This is a Bass tab."; }
		 */
		
		for (int i = 0; i < lines.length; i++) {
			for (int j = 0; j < lines[i].length(); j++) {
				//the current character
				String curr = lines[i].substring(j, j + 1);
				
				System.out.print(curr);
				
				if (j == lines[i].length() - 1) 
					System.out.print("\n");
			}
		}
		return "";
	}

	/*public Note getNote(int row, int column) {
		// todo
	} */

	/*
	 * Basic getter function for the measure list.
	 */
	public ArrayList<Measure> getMeasureList() {
		return this.measureList;
	}
}