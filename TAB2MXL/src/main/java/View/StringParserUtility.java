package View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Stack;

import TAB2MXL.Beam;
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
			System.out.println("Detected repeats");
			System.out.println("Attempted to remove repeat-related symbols. Result:");
			for (String l : lines) {
				System.out.println(l);
			}
		} else {
			lines = rawLines;
		}
		
		String splitLines[][] = new String[lines.length][]; // splitLines[row][column]
		
		// Split up each line by "|", and put those arrays into the splitLines array.
		for (int i = 0; i < lines.length; i++) {
			String currLine[] = lines[i].split("\\|");
			splitLines[i] = currLine;
		}
		

		int measureCount = 0;
		int numMeasures = splitLines[0].length - 1;
		String[] measureArray = new String[numMeasures];
		
		System.out.println(numMeasures);

		
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
		
		fillBeams(measureList);
		return measureList;
	}
	
	// any sequence that adds up to a quarter note, put beams in it
	public static void fillBeams(ArrayList<Measure> measureList) {
		HashMap<String, Float> noteEnum = new HashMap<>();
		HashMap<String, Integer> noteTypeToBeamNumber = new HashMap<>();
		
		noteEnum.put("whole", 1f);
		noteEnum.put("half", 0.5f);
		noteEnum.put("quarter", 0.25f);
		noteEnum.put("eighth", 0.125f);
		noteEnum.put("16th", 0.0625f);
		noteEnum.put("32nd", 0.03125f);
		noteEnum.put("64th", 0.015625f);
		
		noteTypeToBeamNumber = new HashMap<>();
		noteTypeToBeamNumber.put("eighth", 1);
		noteTypeToBeamNumber.put("16th", 2);
		noteTypeToBeamNumber.put("32nd", 3);
		noteTypeToBeamNumber.put("64th", 4);
		
		int beamNumber = 1;
		for (int i = 0; i < measureList.size(); i++) {
			int trailer = 0;
			int leader = 1;
			float currSum = 0;
			ArrayList<Note> noteList = measureList.get(i).noteList;
			while (leader < noteList.size() && trailer != leader) {
				if (noteEnum.get(noteList.get(leader).type) > 0.125f) {
					trailer = leader + 1;
					leader = trailer + 1;
					continue;
				}
				
				
				for (int j = trailer; j <= leader; j++) {
					float num = noteEnum.get(noteList.get(j).getType());
					currSum += num;
				}
				
				if (currSum > 0.25f) {
					trailer++;
					if (trailer == leader) {
						leader++;
					}
					currSum = 0;
					continue;
					
				} else if (currSum < 0.25f) {
					leader++;
					continue;
				} else {
					// Beam found
					// beginning beam
					System.out.printf("Found a beam from notes <%s>-<%s>\n", trailer, leader);
					beamNumber = noteTypeToBeamNumber.get(noteList.get(trailer).type);
					noteList.get(trailer).beam = new Beam(beamNumber, "begin");
					
					// middle beams
					for (int j = trailer + 1; j < leader; j++) {
						beamNumber = noteTypeToBeamNumber.get(noteList.get(trailer).type);
						noteList.get(j).beam = new Beam(beamNumber, "continue");
					}
					
					// Ending beam
					beamNumber = noteTypeToBeamNumber.get(noteList.get(trailer).type);
					noteList.get(leader).beam = new Beam(beamNumber, "end");	
					
					trailer = leader + 1;
					leader = trailer + 1;
					currSum = 0;
					continue;
				}		
			}
		}
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
		
				if (ss.matches("\\|\\|\\*.")) { 
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
				sb = sb.deleteCharAt(doubleBarIndex);
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
				//System.out.println("fret: " + note.fret + " string: " + note.string + " isChord: " + note.isChord);
			}
			else 
			{
				currColumn = note.column;
			}
		}
	}
	
	public static Measure measureParser(String measureString) {
		Measure measure = new Measure(getDivison(measureString));
		measure.divisions = getDivison(measureString);
		
		String lines[] = measureString.split("\\r?\\n");
		
		for (int i = 0; i < lines[0].length() - 1; i++) { // i are the columns
			for (int j = 0; j < lines.length; j++) { // j are the rows
				String curr = lines[j].substring(i, i + 1); //this is the current character that we are parsing
				if (!curr.equals("-") && !(curr.contains("*"))) { // this must be a note!
					Note note = getNote(j, Integer.parseInt(curr));
					note.setColumn(i);
					note.duration = getDuration(lines, i); //pass the current column index
					//System.out.println((float) note.getDuration() / (float) measure.getDivision());
					note.setType(NoteUtility.getNoteType((float) note.getDuration() / (float) measure.getDivision()));
					//System.out.println("fret: " + note.fret + " string: " + note.string + " duration: " + note.duration + " type: " + note.getType() + " division :" + measure.getDivision()); // for testing
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
        int duration = 1;
        for (int i = column + 1; i <= lines[0].length() - 1; i++) { // i are the columns
            for (int j = 0; j < lines.length; j++) { // j are the rows
                String curr = lines[j].substring(i, i + 1);
                if (!(curr.equals("-"))) { // does this work once we get holding/pulling?
                    return duration;
                }
            }
            duration++;
        }
        return duration;
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

