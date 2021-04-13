package View;

import java.util.ArrayList;
import java.util.Hashtable;

import TAB2MXL.BassNoteUtility;
import TAB2MXL.Measure;
import TAB2MXL.Note;

public class StringParserUtilityBass extends StringParserUtility {

	public static ArrayList<Measure> measureList = new ArrayList<Measure>();
	public static Hashtable<Integer, Integer> measureRepeats = new Hashtable<Integer, Integer>();

	public static Note getNote(int string, int fret) {
		BassNoteUtility noteGetter = TuningController.BNU;
		noteGetter.initialise();
		return noteGetter.getBassNote(string, fret);
	}

	public ArrayList<Measure> getMeasureList() {
		return StringParserUtilityBass.measureList;
	}
}