package View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import TAB2MXL.DrumNote;
import TAB2MXL.Measure;
import TAB2MXL.Note;
import TAB2MXL.NoteUtility;

public class StringParserUtilityDrum extends StringParserUtility {
	
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
			// Pass on measure array and also the instrument to be parsed returns a measure object/
			measureList.add(measureParser(measureArray[i], splitLines));
			
			setChord(measureList.get(i).getNoteList());
			measureList.get(i).measureNumber = i + 1;
			measureList.get(i).setTimeSignature(Controller.beatType);
			
			Map<Integer, Integer> timeList = Controller.beatList;
			if(timeList.containsKey(i+1)) {
				measureList.get(i).setTimeSignature(timeList.get(i+1));
			}
			
		}
		return measureList;
	}
	
	public static Measure measureParser(String measureString, String[][] splitLines) {
		Measure measure = new Measure(getDivison(measureString));
		Measure.divisions = getDivison(measureString);
		
		String lines[] = measureString.split("\\r?\\n");
		
		for (int i = 0; i < lines[0].length() - 1; i++) { // i are the columns
			for (int j = 0; j < lines.length; j++) { // j are the rows
				//String instrument = getInstrument(lines, i);
				String instrument = splitLines[j][0];
				String curr = lines[j].substring(i, i + 1); //this is the current character that we are parsing
				if (!(curr.equals("-"))) { // this must be a note!
					Note note = getNote(instrument);
					if(curr.equals("x")) ((DrumNote) note).setNotehead("x");
					note.setColumn(i);
					note.duration = getDuration(lines, i); //pass the current column index
					note.setType(NoteUtility.getNoteType((float) note.getDuration() / (float) Measure.divisions));
					System.out.println("instrument " + instrument + " string: " + note.string + " duration: " + note.duration + " type: " + note.getType()); // for testing
					measure.noteList.add(note);
				}
			}
		}
		return measure;
	}

	public static Note getNote(String instrument) {
		NoteUtility noteGetter = new NoteUtility();
		noteGetter.initializeDrum();
		return noteGetter.drumNotes.get(instrument);
	}
}

