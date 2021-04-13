package View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import TAB2MXL.Beam;
import TAB2MXL.GraceNote;
import TAB2MXL.Measure;
import TAB2MXL.Note;
import TAB2MXL.NoteUtility;
import javafx.util.Pair;

public class StringParserUtility {

	public static ArrayList<Measure> measureList = new ArrayList<Measure>();
	public static Hashtable<Integer, Integer> measureRepeats = new Hashtable<Integer, Integer>();
	public static Map<Integer, Pair<Integer, Integer>> timeList = Controller.beatList;

	public static int hammerOnCount = 1;
	public static int pullOffCount = 1;
	public static int slideCount = 1;

	public static void clearMeasureList() {
		StringParserUtility.measureList = new ArrayList<Measure>();
	}

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

			for (int i = 0; i < measureArray.length; i++) { // calling measureParse on each individual measure in the
				// measureArray
				measureList.add(measureParser(measureArray[i], globalMeasureNumber++));
				setChord(measureList.get(measureIndex++).getCompleteNoteList());
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

	// any sequence that adds up to a quarter note, put beams in it
	public static void fillBeams(ArrayList<Measure> measureList) throws Exception {
		HashMap<String, Float> noteEnum = new HashMap<>();
		HashMap<String, Integer> beamNumberGuide = new HashMap<>();
		noteEnum.put("whole", 1f);
		noteEnum.put("half", 0.5f);
		noteEnum.put("quarter", 0.25f);
		noteEnum.put("eighth", 0.125f);
		noteEnum.put("16th", 0.0625f);
		noteEnum.put("32nd", 0.03125f);
		noteEnum.put("64th", 0.015625f);

		beamNumberGuide.put("eighth", 1);
		beamNumberGuide.put("16th", 2);
		beamNumberGuide.put("32nd", 3);
		beamNumberGuide.put("64th", 4);

		int beamNumber = 1;
		for (int i = 0; i < measureList.size(); i++) {
			int trailer = 0;
			int leader = 1;
			float currSum = 0;
			ArrayList<Note> noteList = getNoteListToBeam(measureList.get(i).noteList);

			Iterator<Note> iter = noteList.iterator();

			while (iter.hasNext()) {
				Note element = iter.next();
				if (element.isGrace) {
					iter.remove();
				}
			}

			while (leader < noteList.size() && trailer != leader) {
				currSum = 0;

				if (!noteEnum.containsKey(noteList.get(leader).type)) {
					throw new Exception(String.format(
							"Found a note without a specified type." + "string:<%s> fret:<%s> column:<%s> type:<%s> ",
							noteList.get(leader).string, noteList.get(leader).fret, noteList.get(leader).column,
							noteList.get(leader).type));
				}

				if (noteEnum.get(noteList.get(leader).type) > noteEnum.get("eighth")) {
					trailer = leader + 1;
					leader = trailer + 1;
					continue;
				}

				for (int j = trailer; j <= leader; j++) {
					float num = noteEnum.get(noteList.get(j).getType());
					currSum += num;
				}

				if (currSum > noteEnum.get("quarter")) {
					trailer++;
					if (trailer == leader) {
						leader++;
					}
					continue;

				} else if (currSum < noteEnum.get("quarter")) {
					leader++;
					continue;
				} else {
					// Beam found
					// beginning beam
					System.out.printf("Found a beam from notes <%s>-<%s> in Measure <%s>\n", trailer, leader, i);
					ArrayList<Integer> beamRange = new ArrayList<Integer>();

					beamNumber = beamNumberGuide.get(noteList.get(trailer).type);
					beamRange.add(beamNumber);

					if (beamNumber > 1) {
						for (int newBeamRange = 1; newBeamRange < beamNumber; newBeamRange++) {
							beamRange.add(newBeamRange);
						}
						Collections.sort(beamRange);
					}

					// Begin all beam(s)
					for (int b : beamRange) {
						noteList.get(trailer).beamList.add(new Beam(b, "begin"));
					}

					// middle beams
					for (int j = trailer + 1; j < leader; j++) {
						beamNumber = beamNumberGuide.get(noteList.get(trailer).type);

						if (!beamRange.contains(beamNumber)) { // new beam number introduced

							for (int b : beamRange) {
								noteList.get(j).beamList.add(new Beam(b, "continue"));
							}

							beamRange.add(beamNumber);
							Collections.sort(beamRange);
							noteList.get(j).beamList.add(new Beam(beamNumber, "begin"));
						} else { // no new numbers introduced, continue everything
							for (int b : beamRange) {
								noteList.get(j).beamList.add(new Beam(b, "continue"));
							}
						}
					}

					// Ending beam
					beamNumber = beamNumberGuide.get(noteList.get(trailer).type);

					for (int b : beamRange) {
						noteList.get(leader).beamList.add(new Beam(b, "end"));
					}

					trailer = leader + 1;
					leader = trailer + 1;
				}
			}
		}
	}

	public static ArrayList<Note> getNoteListToBeam(ArrayList<Note> noteList) {
		ArrayList<Note> noteListToBeam = new ArrayList<Note>();

		int currColumn = -1;
		boolean hitChord = false;
		for (int j = 0; j < noteList.size(); j++) {
			Note n = noteList.get(j);

			// New note in new column
			if (n.column != currColumn) {
				hitChord = false;
				currColumn = n.column;
				noteListToBeam.add(n);
			}

			if (hitChord && n.isChord) {
				continue;
			} else if (n.isChord) {
				hitChord = true;
			}
		}
		return noteListToBeam;
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
			ss = starLine.substring(i, i + 4);

			if (ss.matches("\\|\\|\\*.")) {
				currMeasure++;
				System.out.println("Starting a repeat in measure " + currMeasure);
				if (repeatStart) { // already started a repeat
					throw new Exception("Error - uneven repeat bars");
				}

				repeatStart = true;
				repeatEnd = false;
				getMeasureByNum(currMeasure).repeatStart = true;
				currStart = currMeasure;

				// end of a repeat, i.e. "-*||"
			} else if (ss.matches(".\\*\\|\\|")) {
				System.out.println("Ending a repeat on measure " + currMeasure);
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

	public static void fillMeasureRepeatsRepeatsAbove(String[] lines) throws Exception {
		// Find the actual first line of measures
		int firstLineIndex = 0;
		while (firstLineIndex < lines.length && lines[firstLineIndex].matches(".+REPEAT.+")) {
			firstLineIndex++;
		}
		int repeatIndex = firstLineIndex - 1;

		if (firstLineIndex == lines.length - 1 || firstLineIndex == 0) {
			throw new Exception("No repeats above measure detected");
		}

		String repeatLine = lines[repeatIndex];
		String firstLine = lines[firstLineIndex];
		System.out.printf("Computing repeats using this line: <\n%s\n>\n", repeatLine);
		// First first | in the repeat line
		int barIndex = getNextBarIndex(lines[firstLineIndex], -1); // skip past the first bar in the line
		boolean inRepeat = false;

		// Special case: check if the first measure is part of a repeat
		if (repeatLine.charAt(barIndex) == '|') {
			System.out.println("First measure is a repeat. Beginning a repeat");
			getMeasureByNum(1).repeatStart = true;
			getMeasureByNum(1).repeats = getRepeatNumAbove(repeatLine, barIndex);
			inRepeat = true;
		}

		int currMeasure = 1;

		for (int charIndex = barIndex + 1; charIndex < repeatLine.length(); charIndex++) {
			char thisChar = firstLine.charAt(charIndex);

			if (thisChar == '|') {
				if (repeatLine.charAt(charIndex) == '|') {
					System.out.println("Detected a | at index " + charIndex + " in measure " + currMeasure);
					// found a measure | in firstLine in the same place as a | in repeatLine

					if (inRepeat) { // This marks the end of a repeat
						inRepeat = false;
						getMeasureByNum(currMeasure).repeatEnd = true;
						getMeasureByNum(currMeasure).repeats = getRepeatNumAbove(repeatLine, barIndex);
						System.out.println("Ending repeat at measure " + currMeasure);

						// Check if a repeat is starting for the next measure
						if (charIndex < repeatLine.length() - 1 && repeatLine.charAt(charIndex + 1) == '-') {
							System.out.println("Next measure is part of a new repeat. Starting a repeat");
							inRepeat = true;
							getMeasureByNum(currMeasure + 1).repeatStart = true;
							getMeasureByNum(currMeasure + 1).repeats = getRepeatNumAbove(repeatLine, charIndex);
						}
					} else { // this marks the beginning of a repeat
						System.out.println("Starting repeat at measure " + currMeasure + 1);
						inRepeat = true;
						getMeasureByNum(currMeasure + 1).repeatStart = true;
						getMeasureByNum(currMeasure + 1).repeats = getRepeatNumAbove(repeatLine, charIndex);
					}
					barIndex = charIndex;
				}
				currMeasure++;
			}
		}
	}

	private static int getNextBarIndex(String line, int start) {
		int barIndex = start + 1;
		while (!(line.charAt(barIndex) == '|')) {
			barIndex++;
		}
		if (barIndex >= line.length()) {
			return -1;
		}
		return barIndex;
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

	public static String[] convertRepeatsToNormalRepeatsAbove(String[] lines) {
		// String newLines[] = new String[lines.length];
		ArrayList<String> newLinesList = new ArrayList<String>();

		for (int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
			if (!lines[lineIndex].matches(".+REPEAT.+")) {
				newLinesList.add(lines[lineIndex]);
			}
		}

		String[] newLines = new String[newLinesList.size()];
		newLines = newLinesList.toArray(newLines);

		System.out.println("Detected repeats above tablature. This is the clean version:");

		for (String line : newLines) {
			System.out.println(line);
		}
		return newLines;
	}

	// Get the repeat number, such as 4 in "4|", given the location of the ending
	// "*" in that measure
	private static int getRepeatNum(String[] lines, int starRow, int starCol) {
		return Character.getNumericValue(lines[0].charAt(starCol + 2));
	}

	private static int getRepeatNumAbove(String repeatLine, int barIndex) {
		int repeatNum = 0;
		for (int i = barIndex; i < repeatLine.length(); i++) {
			if (repeatLine.charAt(i) == 'R') {
				repeatNum = Character.getNumericValue(repeatLine.charAt(i + 7));
			}
		}
		return repeatNum;
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
			if (note.isGrace == true) {
				continue;
			}
			else if (currColumn == note.column) {
				note.isChord = true;
			} 
			else {
				currColumn = note.column;
			}
			System.out.println("fret: " + note.fret + " string: " + note.string + " duration: " + note.duration + " type: "
					+ note.getType() + " column: " + note.column + " ischord: " + note.isChord);
		}
	}

	public static int doubleDigitCounter(String measure, int fromIndex) { // use chars in future
		int count = 0;
		String lines[] = measure.split("\\r?\\n");

		for (int i = fromIndex; i < lines[0].length() - 1; i++) { // i are the columns
			for (int j = 0; j < lines.length; j++) { // j are the rows
				String curr = lines[j].substring(i, i + 2);
				if (isNumeric(curr)) {
					count++;
					break; // breaks the recent loop so it goes to the next column
				}
			}
		}
		return count;
	}

	public static Measure measureParser(String measureString, int measureNum) {
		Measure measure = new Measure(getDivison(measureString));
		measure.measureNumber = measureNum;

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
				String curr = lines[j].substring(i, i + 1); // this is the current character that we are parsing
				if (i > 0) {
					String prev = lines[j].substring(i - 1, i);
					if (isNumeric(curr)) {
						String currNote = getNoteString(lines, i, j);
						if (isDash(prev)) {
							if (isNumeric(currNote)) { // double or single
								parsingFunction(currNote, lines, measure, i, j, timeSignature, false);
							} else if (currNote.matches("^\\d{1,2}[psh]{1}\\d{1,2}$")) {
								String complexType = currNote.replaceAll("\\d", ""); // only left with p, h, or s
								String[] noteSplit = currNote.split(complexType); // 13p15 -> p , s , h

								parsingFunctionComplex(noteSplit, lines, measure, i, j, timeSignature, complexType,
										false);
							}
						}
					} else if (curr.equals("[")) {
						String currNote = getNoteString(lines, i + 1, j);
						parsingFunction(currNote, lines, measure, i + 1, j, timeSignature, true);
					} 
					else if (curr.equals("g")) {
						String currNote = getNoteString(lines, i + 1, j);
						String[] noteSplit = currNote.split("[sph]");

						String complexType = currNote.replaceAll("\\d", ""); // only left with p, h, or s

						parsingFunctionComplex(noteSplit, lines, measure, i + 1, j, timeSignature, complexType, true);
					}
				} else if (i == 0) { //only when i = 0
					if (isNumeric(curr)) {
						String currNote = getNoteString(lines, i, j);
						if (isNumeric(currNote)) {
							parsingFunction(currNote, lines, measure, i, j, timeSignature, false);
						} else if (currNote.matches("^\\d{1,2}[psh]{1}\\d{1,2}$")) { //
							String[] noteSplit = currNote.split("^[hps]$");
							String complexType = currNote.replaceAll("\\d", ""); // only left with p, h, or s
							parsingFunctionComplex(noteSplit, lines, measure, i, j, timeSignature, complexType, false);
						}
					} else if (curr.equals("[")) {
						String currNote = getNoteString(lines, i + 1, j);
						parsingFunction(currNote, lines, measure, i + 1, j, timeSignature, true);
					} else if (curr.equals("g")) {
						String currNote = getNoteString(lines, i + 1, j);
						String[] noteSplit = currNote.split("[phs]");

						String complexType = currNote.replaceAll("\\d", ""); // only left with p, h, or s

						parsingFunctionComplex(noteSplit, lines, measure, i + 1, j, timeSignature, complexType, true);
					}
				}
			}
		}
		return measure;
	}

	public static String getNoteString(String[] lines, int i, int j) { // i = column, j = row
		String curr = "";
		int index = lines[j].indexOf("-", i);

		if (!(index < 0)) {
			curr = lines[j].substring(i, index);
			if (!curr.contains("]"))
				return curr;
		}
		index = lines[j].indexOf("]", i);
		if (!(index < 0)) {
			curr = lines[j].substring(i, index);
			if (isNumeric(curr))
				return curr;
		}
		curr = lines[j].substring(i, lines[j].length());
		return curr;
	}

	public static void parsingFunction(String currNote, String[] lines, Measure measure, int i, int j,
			float timeSignature, boolean isHarmonic) {
		System.out.println("currNote :" + currNote);
		Note note = getNote(j, Integer.parseInt(currNote));

		note.setColumn(i + currNote.length() - 1); // index
		note.stem = "up";
		note.floatDuration = (float) (getDuration(lines, i + currNote.length() - 1) * timeSignature); // pass the

		note.isHarmonic = isHarmonic;
		note.setType(NoteUtility.getNoteType((float) note.getFloatDuration() / (float) measure.getDivision(), note));

		System.out.println("fret: " + note.fret + " string: " + note.string + " duration: " + note.duration + " type: "
				+ note.getType() + " division :" + measure.getDivision() + " column: " + note.column); // test

		measure.noteList.add(note);
		measure.completeNoteList.add(note);
	}

	public static void parsingFunctionComplex(String[] noteSplit, String[] lines, Measure measure, int i, int j,
			float timeSignature, String complexType, boolean isGrace) {
		int lengthTracker = 0;
		if (isGrace) {
			int indexAdder = 0;
			ArrayList<Note> noteList = new ArrayList<Note>();

			StringBuilder sb = new StringBuilder();
			sb.append("g");
			sb.append(complexType);

			String[] complexTypeList = sb.toString().split("");

			for (int k = 0; k < noteSplit.length; k++) {
				String currNoteString = noteSplit[k];
				Note currNote = getNote(j, Integer.parseInt(currNoteString));
				currNote.stem = "up";
				currNote.setType(
						NoteUtility.getNoteType(currNote.getFloatDuration() / (float) measure.getDivision(), currNote));

				if (k >= 2) {
					indexAdder++;
				}

				if (k == 0) {
					currNote.setColumn(i + currNoteString.length() - 1);
					currNote.floatDuration = (float) (getDuration(lines, i + currNoteString.length() - 1)
							* timeSignature);
				} else {
					currNote.setColumn(i + currNoteString.length() + lengthTracker + indexAdder);
					currNote.floatDuration = (float) (getDuration(lines,
							i + currNoteString.length() + lengthTracker + indexAdder) * timeSignature);
				}

				currNote.setType(
						NoteUtility.getNoteType(currNote.getFloatDuration() / (float) measure.getDivision(), currNote));

				/*
				System.out.println("current note duration : "
						+ (float) currNote.getFloatDuration() / (float) measure.getDivision());
				System.out.println("firstNote information : fret: " + currNote.fret + " string: " + currNote.string
						+ " duration: " + currNote.duration + " type: " + currNote.getType() + " division :"
						+ measure.getDivision() + " column: " + currNote.column); // test */

				lengthTracker += currNoteString.length();
				noteList.add(currNote);
				measure.completeNoteList.add(currNote);
			}

			GraceNote graceNote = new GraceNote(noteList, complexTypeList);
			measure.graceNotes.add(graceNote);
			measure.noteList.add(new Note(graceNote, true));
			
		} else { // note a grace note
			for (int k = 0; k < noteSplit.length; k++) {
				String currNoteString = noteSplit[k];

				Note currNote = getNote(j, Integer.parseInt(currNoteString));
				currNote.stem = "up";
				currNote.complexType = complexType;

				if (k == 0) {
					currNote.start();
					currNote.setColumn(i + currNoteString.length() - 1);
					currNote.floatDuration = (float) (getDuration(lines, i + currNoteString.length() - 1)
							* timeSignature);
				} 
				if (k == 1) {
					currNote.stop();
					currNote.setColumn(i + lengthTracker + currNoteString.length());
					currNote.floatDuration = (float) (getDuration(lines, i + currNoteString.length() + lengthTracker)
							* timeSignature);
				}

				currNote.setType(
						NoteUtility.getNoteType(currNote.getFloatDuration() / (float) measure.getDivision(), currNote));

				switch (complexType) {
				case "p":
					currNote.complexTypeNumber = pullOffCount;
					if (k == 1) {
						pullOffCount++;
					}

				case "h":
					currNote.complexTypeNumber = hammerOnCount;
					if (k == 1) {
						hammerOnCount++;
					}

				case "s":
					currNote.complexTypeNumber = slideCount;
					if (k == 1) {
						slideCount++;
					}
				}
				/*
				System.out.println("current note duration : "
						+ (float) currNote.getFloatDuration() / (float) measure.getDivision());
				System.out.println("firstNote information : fret: " + currNote.fret + " string: " + currNote.string
						+ " duration: " + currNote.duration + " type: " + currNote.getType() + " division :"
						+ measure.getDivision() + " column: " + currNote.column); // test */

				lengthTracker += currNoteString.length();
				measure.noteList.add(currNote);
				measure.completeNoteList.add(currNote);
			}
		}
	}

	public static int getDivison(String measure) { // returns the division of a measure, only works for guitar/bass
		int division = 0;
		int minIndex = Integer.MAX_VALUE;
		String lines[] = measure.split("\\r?\\n");

		int[][] checkedIndices = new int[lines[0].length()][lines.length];

		for (int i = 0; i < lines[0].length() - 1; i++) { // i are the columns
			for (int j = 0; j < lines.length; j++) { // j are the rows
				String curr = lines[j].substring(i, i + 1);
				if (checkedIndices[i][j] == -1) {
					continue; // go next
				} else if (curr.equals("g")) {
					String currNote = getNoteString(lines, i + 1, j); // index = i + 1 + currNote.length
					int graceNoteLength = currNote.length();
					int currNoteIndex = graceNoteLength + i;

					for (int k = i; k <= graceNoteLength; k++) {
						checkedIndices[k][j] = -1;
					}
					minIndex = Math.min(minIndex, currNoteIndex);
				} else if (isNumeric(curr)) {
					minIndex = Math.min(minIndex, i);
				}
			}
		}
		division = lines[0].length() - minIndex;
		return division - doubleDigitCounter(measure, minIndex);
	}

	public static int getDuration(String lines[], int column) { // uses isNumeric as a check so it only works for
																// guitar/bass
		int duration = 1;
		for (int i = column + 1; i <= lines[0].length() - 1; i++) { // i are the columns
			for (int j = 0; j < lines.length; j++) { // j are the rows
				String curr = lines[j].substring(i, i + 1);
				if (isNumeric(curr)) { // does this work once we get holding/pulling?, replace this with isNumeric
					return duration;
				}
			}
			duration++;
		}
		return duration;
	}

	public static boolean isNumeric(String str) {
		return str.matches("^\\d{1,2}$"); // match a number with optional '-' and decimal.
	}

	public static boolean isLetter(String str) {
		return str.matches("[a-zA-Z]");
	}

	public static boolean isDash(String str) {
		return str.equals("-");
	}

	public static Note getNote(int string, int fret) {
		NoteUtility noteGetter = TuningController.NU;
		return noteGetter.getGuitarNote(string, fret);
	}

	public static Measure getMeasureByNum(int measureNum) {
		for (Measure m : measureList) {
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