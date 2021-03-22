package View;

import java.util.ArrayList;
import java.util.Arrays;

import TAB2MXL.BassNoteUtility;
import TAB2MXL.Measure;
import TAB2MXL.Note;
import TAB2MXL.NoteUtility;


//Can handle a 4 string bass

public final class StringParserUtilityBass {

	public static ArrayList<Measure> measureList = new ArrayList<Measure>();

	public static ArrayList<Measure> stringParse(String input) { // potentially take timeBeatType here
		String lines[] = input.split("\\r?\\n");
		String splitLines[][] = new String[lines.length][]; // splitLines[row][column]
		
		System.out.println("lines: " + lines[0].length());

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
			measureList.add(measureParser(measureArray[i]));
			setChord(measureList.get(i).getNoteList());
			measureList.get(i).measureNumber = i + 1;
		}
		return measureList;
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
				if (!(curr.equals("-"))) { // this must be a note!
					Note note = getNote(j, Integer.parseInt(curr));
					note.setColumn(i);
					note.duration = getDuration(lines, i); //pass the current column index
					note.setType(NoteUtility.getNoteType((float) note.getDuration() / (float) measure.getDivision()));
					System.out.println("fret: " + note.fret + " string: " + note.string + " duration: " + note.duration + " type: " + note.getType()); // for testing
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
					System.out.println("division: " + division);
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
		BassNoteUtility noteGetter = new BassNoteUtility();
		noteGetter.initialise();
		return noteGetter.BassNote[string][fret];
	}

	public ArrayList<Measure> getMeasureList() {
		return StringParserUtilityBass.measureList;
	}
}

