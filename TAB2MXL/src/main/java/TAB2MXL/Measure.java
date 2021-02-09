package TAB2MXL;

import java.util.ArrayList;

public class Measure {
	
	public int measureNumber;
	
	public ArrayList<Note> noteList;
	public static int divisions;
		
	public int keyFifths = 0; //always 0
	
	public int timeBeats; // user input 	
	public int timeBeatType; // user input
	
	public static final String clefSign = "TAB";
	public int clefLine;

	// Create a new measure
	public Measure(int clefLine, int timeBeats, int timeBeatType, int measureNumber) {
		
		this.measureNumber=measureNumber;
		this.noteList = new ArrayList<Note>();
		Measure.divisions = 2; // Change this?
		this.measureNumber = measureNumber;
		this.noteList = new ArrayList<Note>();
		this.clefLine = clefLine;
		this.clefLine=clefLine;		
		this.timeBeats = timeBeats;
		this.timeBeatType = timeBeatType;
	}
	
	// Create measure based on existing note list
	public Measure(ArrayList<Note> noteList) {
		this.noteList = noteList;
	}
}