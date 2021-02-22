package View;

import java.util.ArrayList;
import java.util.Arrays;

import TAB2MXL.Measure;
import TAB2MXL.Note;
import TAB2MXL.NoteUtility;

public final class StringParserUtility {

	public ArrayList<Measure> measureList = new ArrayList<Measure>();

	public static void stringParse(String input) { // potentially take timeBeatType here
		String lines[] = input.split("\\r?\\n");
		String splitLines[][] = new String[lines.length][]; // splitLines[row][column]
		
		System.out.println("lines: " + lines[0].length());

		int guitarLines = 6;
		// Split up each line by "|", and put those arrays into the splitLines array.
		for (int i = 0; i < lines.length; i++) {
			String currLine[] = lines[i].split("\\|");
			splitLines[i] = currLine;
			System.out.println(splitLines[i][0]); // Prints the first entry of each line/array.. testing
		}

		System.out.println(Arrays.deepToString(splitLines)); // prints the second line which is now split into multiple arrays... testing

		int numMeasures = splitLines[0].length - 1;
		System.out.println("numMeasures: " + numMeasures); //testing
		int measureCount = 0;
		String[] measureArray = new String[numMeasures];

		for (int j = 1; j <= numMeasures; j++) {
			String measure = "";
			for (int i = 0; i < splitLines.length; i++) { // splitlines.length = how many lines there are
				String currString = splitLines[i][j];
				measure += currString;
				System.out.println(currString); //testing
				measure += "\n"; //testing
			}
			System.out.println(""); //testing
			measureArray[measureCount] = measure;
			measureCount++;
		}
		System.out.println(Arrays.deepToString(measureArray));
		
		//call measureParser
		for (int i = 0; i < measureArray.length; i++) {
			System.out.println(getDivison(measureArray[i]));
		}
	}

	public static String checkTabType(String input) {
		// split the input into different lines
		String lines[] = input.split("\\r?\\n");
		if (lines[0].toUpperCase().startsWith("E")) {
			System.out.println("This is a Guitar Tab.");
		} else if (lines[0].toUpperCase().startsWith("C")) {
			System.out.println("This is a Drum Tab.");
		} else if (lines[0].toUpperCase().startsWith("G")) {
			System.out.println("This is a Bass Tab.");
		}
		return "";
	}
	
	public static Measure measureParser(String measureString) {
		Measure measure = new Measure(getDivison(measureString));
		String lines[] = measureString.split("\\r?\\n");
		
		return measure;
	}
	
	public static int getDivison(String measure) {
		int division = 0;
		String lines[] = measure.split("\\r?\\n");
		
		for (int i = 0; i < lines[0].length() - 1; i++) { // j are the columns
			for (int j = 0; j < lines.length; j++) { // i are the rows

				String curr = lines[j].substring(i, i + 1);
				if (!(curr.equals("-"))) { // does this work once we get holding/pulling?
					division = lines[i].length() - i;
					System.out.println("note length: " + getNoteLength(lines, i));
					return division;
				}
			}
		}
		return division;
	}
	
    public static int getNoteLength(String lines[], int column) {
        int noteLength = 1;
        for (int i = column + 1; i < lines[0].length() - 1; i++) { // j are the columns
            for (int j = 0; j < lines.length; j++) { // i are the rows
                String curr = lines[j].substring(i, i + 1);
                if (!(curr.equals("-"))) { // does this work once we get holding/pulling?
                    return noteLength;
                }
                if (i == lines[0].length() - 2 && j == lines.length - 1)
                	noteLength++;
            }
            noteLength++;
        }
        return noteLength;
    } 

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional '-' and decimal.
	}

	public Note getNote(int row, int column) {
		NoteUtility noteGetter = new NoteUtility();
		noteGetter.initialise();
		return noteGetter.guitarNote[row][column];
	}

	public ArrayList<Measure> getMeasureList() {
		return this.measureList;
	}
}

