package View;

import java.util.ArrayList;
import java.util.Map;

import TAB2MXL.DrumNote;
import TAB2MXL.Measure;
import TAB2MXL.Note;
import TAB2MXL.NoteUtility;
import javafx.util.Pair;

public class StringParserUtilityDrum extends StringParserUtility {

	static Map<Integer, Pair<Integer, Integer>> timeList = Controller.beatList;

	public static ArrayList<Measure> stringParse(String rawInput) throws Exception { // potentially take timeBeatType
																						// here
		String measureGroups[] = rawInput.split("\\r?\\n\\r?\\n");

		int globalMeasureNumber = 1;
		int measureIndex = 0;

		for (String input : measureGroups) {
			// Amer's repeat function
			String rawLines[] = input.split("\\r?\\n");
			String[] lines;
			// Change all instances of || into |; will parse repeats separately
			boolean has_repeats = false;
			boolean has_repeats_above = false;
			if (rawLines[0].matches(".*\\|\\|.*")) {
				lines = convertRepeatsToNormal(rawLines);
				has_repeats = true;
			} else if (rawLines[0].matches(".+REPEAT.+")) {
				lines = convertRepeatsToNormalRepeatsAbove(rawLines);
				has_repeats_above = true;
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
				measureList.add(measureParser(measureArray[i], splitLines, globalMeasureNumber++));
				setChord(measureList.get(measureIndex++).getNoteList());
			}

			// Set measure repeats, if any
			if (has_repeats) {
				fillMeasureRepeats(rawLines);
			} else if (has_repeats_above) {
				fillMeasureRepeatsRepeatsAbove(rawLines);
			}
		}
		fillBeams(measureList);
		return measureList;
	}

	public static Measure measureParser(String measureString, String[][] splitLines, int measureNum) {
		measureString = measureString.replaceAll("X", "x");
		measureString = measureString.replaceAll("O", "o");
		System.out.println(measureString);
		
		Measure measure = new Measure(getDivison(measureString));
		measure.measureNumber = measureNum;
		measure.divisions = getDivison(measureString);
		
		Pair<Integer, Integer> pair = new Pair<Integer, Integer>(Controller.nume, Controller.deno);
		measure.setTimeSignature(Controller.nume);
		measure.setTimeBeatType(Controller.deno);

		if (timeList.containsKey(measureNum)) {
			pair = timeList.get(measureNum);
			measure.setTimeSignature(pair.getKey());
			measure.setTimeBeatType(pair.getValue());
		}
		
		float timeSignature = (float) measure.timeBeats / (float) measure.timeBeatType;

		String lines[] = measureString.split("\\r?\\n");

		for (int i = 0; i < lines[0].length() - 1; i++) { // i are the columns
			for (int j = 0; j < lines.length; j++) { // j are the rows
				// String instrument = getInstrument(lines, i);
				String instrument = splitLines[j][0];
				String curr = lines[j].substring(i, i + 1); // this is the current character that we are parsing
				if ((curr.equals("x") || curr.equals("o"))) { // this must be a note!
					Note note = getNote(instrument);

					if (curr.equals("x")) {
						if (instrument.equals("HH"))
							note = getNote("HHx");
						((DrumNote) note).setNotehead("x");
					}
					note.setColumn(i);			
					note.floatDuration = (float) getDuration(lines, i) * timeSignature;
					note.stem = "up";
					note.string = j; // unsure if this will break anything
					note.setType(NoteUtility.getNoteType((float) note.getFloatDuration() / (float) measure.divisions, note));
					System.out.println("instrument " + instrument + " string: " + note.string + " duration: "
							+ note.duration + " type: " + note.getType() + " division :" + measure.divisions); // for
																												// testing
					measure.noteList.add(note);
				}
			}
		}
		return measure;
	}

	public static Note getNote(String instrument) {
		NoteUtility noteGetter = new NoteUtility();
		noteGetter.initializeDrum();
		return noteGetter.getDrumNote(instrument);
	}

	public static int getDuration(String lines[], int column) { // uses isNumeric as a check so it only works for
		int duration = 1;
		for (int i = column + 1; i <= lines[0].length() - 1; i++) { // i are the columns
			for (int j = 0; j < lines.length; j++) { // j are the rows
				String curr = lines[j].substring(i, i + 1);
				if (curr.equals("x") || curr.equals("o")) {
					return duration;
				} 
			}
			duration++;
		}
		return duration;
	}

	public static int getDivison(String measure) { // returns the division of a measure
		int division = 0;
		String lines[] = measure.split("\\r?\\n");

		for (int i = 0; i < lines[0].length() - 1; i++) { // i are the columns
			for (int j = 0; j < lines.length; j++) { // j are the rows
				String curr = lines[j].substring(i, i + 1);
				if ((curr.equals("x") || curr.equals("o"))) { // does this work once we get holding/pulling?
					division = lines[j].length() - i;
					// System.out.println("division: " + division);
					return division;
				}
			}
		}
		return division;
	}
}