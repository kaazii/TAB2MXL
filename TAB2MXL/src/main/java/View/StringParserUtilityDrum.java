package View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import TAB2MXL.DrumNote;
import TAB2MXL.Measure;
import TAB2MXL.Note;
import TAB2MXL.NoteUtility;
import javafx.util.Pair;

public class StringParserUtilityDrum extends StringParserUtility {
	
	static Map<Integer, Pair<Integer, Integer>> timeList = Controller.beatList;
	
	public static ArrayList<Measure> stringParse(String rawInput) throws Exception{ // potentially take timeBeatType here
		String measureGroups[] = rawInput.split("\\r?\\n\\r?\\n");

		int globalMeasureNumber = 1;
		int measureIndex = 0;

		for (String input : measureGroups) {
			// Amer's repeat function
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
			}

			int measureCount = 0;
			int numMeasures = splitLines[0].length - 1;

			String[] measureArray = new String[numMeasures];

			for (int j = 1; j <= numMeasures; j++) {
				String measure = "";
				for (int i = 0; i < splitLines.length; i++) { // splitlines.length = how many lines there are
					String currString = splitLines[i][j];
					measure += currString;
					measure += "\n"; // testing
				}
				measureArray[measureCount] = measure;
				measureCount++;
			}

			for (int i = 0; i < measureArray.length; i++) {
				measureList.add(measureParser(measureArray[i], splitLines));
				measureList.get(measureIndex).measureNumber = globalMeasureNumber++;
				measureList.get(measureIndex).setTimeSignature(Controller.nume);
				setChord(measureList.get(measureIndex).getNoteList());

				/*
				if(timeList.containsKey(measureList.get(measureIndex).measureNumber)) {
					//only passing on the numerator for now
					measureList.get(measureIndex).setTimeSignature(timeList.get(measureList.get(measureIndex).measureNumber).getKey());
				} */
				measureIndex++;
			}

			// Set measure repeats, if any
			if (has_repeats) {
				fillMeasureRepeats(rawLines);
			}
		}
		//fillBeams(measureList); needs to be re coded
		return measureList;
	}
	
	public static Measure measureParser(String measureString, String[][] splitLines) {
		Measure measure = new Measure(getDivison(measureString));
		measure.divisions = getDivison(measureString);
		
		String lines[] = measureString.split("\\r?\\n");
		
		for (int i = 0; i < lines[0].length() - 1; i++) { // i are the columns
			for (int j = 0; j < lines.length; j++) { // j are the rows
				//String instrument = getInstrument(lines, i);
				String instrument = splitLines[j][0];
				String curr = lines[j].substring(i, i + 1); //this is the current character that we are parsing
				if (!(curr.equals("-"))) { // this must be a note!
					
					Note note = getNote(instrument);
					if(instrument.equals("HH")) note = getNote("HHx");
					if(curr.equals("x")) ((DrumNote) note).setNotehead("x");
					note.setColumn(i);
					note.duration = getDuration(lines, i); //pass the current column index
					note.floatDuration = note.duration / (float) 4;

					note.setType(NoteUtility.getNoteType((float) note.getDuration() / (float) measure.divisions, note));
					System.out.println("instrument " + instrument + " string: " + note.string + " duration: " + note.duration + " type: " + note.getType() + " division :" + measure.divisions); // for testing
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