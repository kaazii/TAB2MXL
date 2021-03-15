package TAB2MXL;

import java.util.ArrayList;

public class Measure {
	
	public int measureNumber;
	
	public ArrayList<Note> noteList = new ArrayList<Note>();
	public static int divisions;
		
	public int keyFifths = 0; //always 0
	
	public static int timeBeats; // user input 	
	public static int timeBeatType; // user input
	
	public static String clefSign = "TAB"; //"TAB" for Guitar
	public int clefLine = 5; //"5" for Guitar

	// Create a new measure
	public Measure(int clefLine, int measureNumber) {
		
		this.measureNumber = measureNumber;
		this.clefLine = clefLine;
	}
	
	public Measure (int division) { //may not need in the future
		Measure.divisions = division;
	}
	
	public Measure() {}
	
	public int getMeasureNumber() {
		return measureNumber;
	}
	public ArrayList<Note> getNoteList() {
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
	
	public int getDivision() {
		return divisions;
	}
}