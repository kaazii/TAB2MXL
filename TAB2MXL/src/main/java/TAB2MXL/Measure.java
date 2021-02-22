package TAB2MXL;

import java.util.ArrayList;

public class Measure {
	
	public int measureNumber;
	
	public ArrayList<Note> noteList;
	public static int divisions;
		
	public int keyFifths = 0; //always 0
	
	public int timeBeats; // user input 	
	public int timeBeatType; // user input
	
	public static final String clefSign = "TAB"; //"TAB" for Guitar
	public int clefLine; //"5" for Guitar


	// Create a new measure
	public Measure(int clefLine, int timeBeats, int timeBeatType, int measureNumber) {
		
		this.measureNumber=measureNumber;
		this.noteList = new ArrayList<Note>();
		Measure.divisions = 2; // Change this?
		this.measureNumber = measureNumber;
		this.noteList = new ArrayList<Note>();
		this.clefLine = clefLine;
		this.timeBeats = timeBeats;
		this.timeBeatType = timeBeatType;
	}
	
	public Measure (int division) { //may not need in the future
		this.divisions = division;
	}
	
	public Measure() {}
	public int getMeasureNumber() {
		return measureNumber;
	}
	public ArrayList<Note> getNotelist() {
		return noteList;
	}
	public int getClefLine() {
		return clefLine;
	}
	public int getTimeBeats() {
		return measureNumber;
	}
	public int gettimeBeatType() {
		return measureNumber;
	}
}