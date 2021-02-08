package TAB2MXL;

import java.util.ArrayList;

public class Measure {
	
	public ArrayList<Note> notelist;
	public static int divisions;
	
	public int keyFifths;
	
	public int timeBeats;
	public int timeBeatType;
	
	public char clefSign;
	public int clefLine;

	// Create a new measure
	public Measure() {
		this.notelist = new ArrayList<Note>();
		Measure.divisions = 2; // Change this?
		
		this.keyFifths = 0;
		
		this.timeBeatType = 4;
	}
	
	// Create measure based on existing note list
	public Measure(ArrayList<Note> notelist) {
		this.notelist = notelist;
	}

}
