package TAB2MXL;

import java.util.ArrayList;
import java.util.Map;

public class Measure {
	
	public int measureNumber;
	
	public ArrayList<Note> noteList = new ArrayList<Note>();
	public static int divisions;
		
	public int keyFifths = 0; //always 0
	
	public int timeBeats; // user input 	
	public int timeBeatType = 4; // user input
	
	public static String clefSign = "TAB"; //"TAB" for Guitar
	public int clefLine = 5; //"5" for Guitar
	
	public boolean repeatStart = false;
	public boolean repeatEnd = false;
	public int repeats = 0;
	
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
	
	//set time signature
	public void setTimeSignature(int timeSig) {
		this.timeBeats = timeSig;
	}
}