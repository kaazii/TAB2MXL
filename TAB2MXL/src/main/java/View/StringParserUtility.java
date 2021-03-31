package View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Stack;

import TAB2MXL.Measure;
import TAB2MXL.Note;
import TAB2MXL.NoteUtility;

public class StringParserUtility {

	public static ArrayList<Measure> measureList = new ArrayList<Measure>();
	public static Hashtable<Integer, Integer> measureRepeats = new Hashtable<Integer, Integer>();
	
	public static void clearMeasureList() {
        StringParserUtility.measureList = new ArrayList<Measure>();
    }
	
	public static ArrayList<Measure> stringParse(String input) throws Exception { // potentially take timeBeatType here
		String rawLines[] = input.split("\\r?\\n");
		String[] lines;
		// Change all instances of || into |; will parse repeats separately
		boolean has_repeats = false;
		if (rawLines[0].matches(".*\\|\\|.*")) {
			lines = convertRepeatsToNormal(rawLines);
			has_repeats = true;
		} else {
			lines = rawLines;
		}
		String splitLines[][] = new String[lines.length][]; // splitLines[row][column]
		
		// Split up each line by "|", and put those arrays into the splitLines array.
		for (int i = 0; i < lines.length; i++) {
			String currLine[] = lines[i].split("\\|");
			splitLines[i] = currLine;
			//System.out.println(splitLines[i][0]); // Prints the first entry of each line/array.. testing
		}

		//System.out.println(Arrays.deepToString(splitLines)); // prints the second line which is now split into multiple arrays... testing

		int numMeasures = splitLines[0].length - 1;
		//System.out.println("numMeasures: " + numMeasures); //testing
		int measureCount = 0;
		String[] measureArray = new String[numMeasures];

		for (int j = 1; j <= numMeasures; j++) {
			String measure = "";
			for (int i = 0; i < splitLines.length; i++) { // splitlines.length = how many lines there are
				String currString = splitLines[i][j];
				measure += currString;
				measure += "\n"; //testing
			}
			measureArray[measureCount] = measure;
			measureCount++;
		}
		//System.out.println(Arrays.deepToString(measureArray));
		
		//call measureParser
		for (int i = 0; i < measureArray.length; i++) {
			measureList.add(measureParser(measureArray[i]));
			setChord(measureList.get(i).getNoteList());
			measureList.get(i).measureNumber = i + 1;
			measureList.get(i).setTimeSignature(Controller.beatType);
			
			Map<Integer, Integer> timeList = Controller.beatList;
			if(timeList.containsKey(i+1)) {
				measureList.get(i).setTimeSignature(timeList.get(i+1));
			}
		}
		
		// Set measure repeats, if any
		if (has_repeats) {
			fillMeasureRepeats(rawLines);
		}
		return measureList;
	}

	public static void fillMeasureRepeats(String[] lines) throws Exception {
		// Find the row on which this instrument puts the * 
		int starRow = getStarRow(lines);
		String starLine = lines[starRow];
		int currMeasure = 1;
		boolean repeatStart = false;
		boolean repeatEnd = false;
		int currStart = 0;
		
		// Edge case - check for a repeat at the beginning
		String ss = starLine.substring(0, 3);
		if (ss.matches("\\|\\|\\*")) {
			currMeasure = 0;
		}
		
		// Iterate through string
		for (int i = 0; i < starLine.length() - 3; i++) {
			ss = starLine.substring(i, i+4);
			
			// hit "*||*", a special case 
			if (ss.equals("*||*")) {
				if (!repeatStart) { // no repeat has been started
					throw new Exception("Error - uneven repeat bars");
				}
				//update current measure
				getMeasureByNum(currMeasure).repeatEnd = true;
				getMeasureByNum(currMeasure).repeats = getRepeatNum(lines, starRow, i);
				getMeasureByNum(currStart).repeats = getRepeatNum(lines, starRow, i);
				
				repeatStart = true;
				repeatEnd = false;
				currMeasure++;
				currStart = currMeasure;
			
			//Beginning of repeat, i.e."||*-"
			} else if (ss.matches("\\|\\|\\*.")) { 
				currMeasure++;
				System.out.println("Starting a repeat in measure "+ currMeasure);
				if (repeatStart) { // already started a repeat
					throw new Exception("Error - uneven repeat bars");
				}
				
				repeatStart = true;
				repeatEnd = false;
				getMeasureByNum(currMeasure).repeatStart = true;
				currStart = currMeasure;
				
			// end of a repeat, i.e. "-*||"
			} else if (ss.matches(".\\*\\|\\|")) { 
				System.out.println("Ending a repeat on measure "+ currMeasure);
				if (repeatEnd || !repeatStart) { // previously ended repeat, or no start
					throw new Exception("Error - uneven repeat bars");
				}
				repeatEnd = true;
				repeatStart = false;
				
				getMeasureByNum(currMeasure).repeatEnd = true;
				getMeasureByNum(currMeasure).repeats = getRepeatNum(lines, starRow, i);
				getMeasureByNum(currStart).repeats = getRepeatNum(lines, starRow, i);
				
				// match "-|-" "0|-" "0|0" etc
			} else if (ss.matches("(-|[0-9]){2}\\|(-|[0-9])")) {
				System.out.println("Adding one empty measure without repeats");
				currMeasure++;
			}
		}
	}
	
	public static String[] convertRepeatsToNormal(String[] lines) {
		String newLines[] = new String[lines.length];
		StringBuilder sb = new StringBuilder();
		int starRow = getStarRow(lines);
		
		if (starRow == -1) {
			return lines;
		}
		
		int doubleBarIndex = lines[starRow].indexOf("||");
		while (doubleBarIndex != -1) {
			// found ||, eliminate them in a column
			for (int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
				if (newLines[lineIndex] == null) {
					sb = new StringBuilder(lines[lineIndex]);
				} else {
					sb = new StringBuilder(newLines[lineIndex]);
				}
				sb = sb.deleteCharAt(doubleBarIndex + 1);
				newLines[lineIndex] = sb.toString();
				newLines[lineIndex] = newLines[lineIndex].replace('*', '-');
			}
			doubleBarIndex = newLines[starRow].indexOf("||");
		}
		return newLines;
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
	
	// Get the repeat number, such as 4 in "4|", given the location of the ending "*" in that measure
	private static int getRepeatNum(String[] lines, int starRow, int starCol) {
		System.out.println(Character.getNumericValue(lines[0].charAt(starCol + 2)));
		return Character.getNumericValue(lines[0].charAt(starCol + 2));
	}
	
	// Find the row on which this instrument puts the * for repeats
	private static int getStarRow(String[] inputLines) {
		for (int i = 0; i < inputLines.length; i++) {
			for (int j = 0; j < inputLines[0].length(); j++) {
				if (inputLines[i].charAt(j) == '*') {
					return i;
				}
			}
		}
		return -1;
	}
	
	public static void setChord(ArrayList<Note> noteList) {
		int currColumn = -1;
		for (Note note : noteList) {
			if (currColumn == note.column) {
				note.isChord = true;
				System.out.println("fret: " + note.fret + " string: " + note.string + " isChord: " + note.isChord);
			}
			else 
			{
				currColumn = note.column;
			}
		}
	}
	
	public static Measure measureParser(String measureString) {
		Measure measure = new Measure(getDivison(measureString));
		Measure.divisions = getDivison(measureString);
		
		String lines[] = measureString.split("\\r?\\n");
		
		for (int i = 0; i < lines[0].length() - 1; i++) { // i are the columns
			for (int j = 0; j < lines.length; j++) { // j are the rows
				String curr = lines[j].substring(i, i + 1); //this is the current character that we are parsing
				if (!curr.equals("-") && !(curr.contains("*"))) { // this must be a note!
					Note note = getNote(j, Integer.parseInt(curr));
					note.setColumn(i);
					note.duration = getDuration(lines, i); //pass the current column index
					System.out.println((float) note.getDuration() / (float) measure.getDivision());
					note.setType(NoteUtility.getNoteType((float) note.getDuration() / (float) measure.getDivision()));
					//System.out.println("fret: " + note.fret + " string: " + note.string + " duration: " + note.duration + " type: " + note.getType()); // for testing
					measure.noteList.add(note);
				}
			}
		}
		return measure;
	}
	
	public static int getDivison(String measure) { //returns the division of a measure
		int division = 0;
		String lines[] = measure.split("\\r?\\n");
		
		for (int i = 0; i < lines[0].length() - 1; i++) { // i are the columns
			for (int j = 0; j < lines.length; j++) { // j are the rows
				String curr = lines[j].substring(i, i + 1);
				if (!(curr.equals("-"))) { // does this work once we get holding/pulling?
					division = lines[j].length() - i;
					//System.out.println("division: " + division);
					return division;
				}
			}
		}
		return division;
	}
	
    public static int getDuration(String lines[], int column) { //note duration/division = type?
        int noteLength = 1;
        for (int i = column + 1; i <= lines[0].length() - 1; i++) { // i are the columns
            for (int j = 0; j < lines.length; j++) { // j are the rows
                String curr = lines[j].substring(i, i + 1);
                if (!(curr.equals("-"))) { // does this work once we get holding/pulling?
                    return noteLength;
                }
            }
            noteLength++;
        }
        return noteLength;
    } 

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional '-' and decimal.
	}

	public static Note getNote(int string, int fret) {
		NoteUtility noteGetter = new NoteUtility();
		noteGetter.initialise();
		return noteGetter.guitarNote[string][fret];
	}

	public static Measure getMeasureByNum(int measureNum) {
		for (Measure m: measureList) {
			if (m.measureNumber == measureNum) {
				return m;
			}
		}
		return null;
	}
	
	public ArrayList<Measure> getMeasureList() {
		return StringParserUtility.measureList;
	}
}