package TAB2MXL;

import java.util.ArrayList;

public class Measure {
	
	public int measureNumber;
	
	public ArrayList<Note> notelist;
	public static int divisions;
		
	public int keyFifths=0;//always 0
	
	public int timeBeats;// user input 	
	public int timeBeatType;// user input
	
	public static final String clefSign = "TAB";
	public int clefLine;

	// Create a new measure
	public Measure(int clefLine, int timeBeats, int timeBeatType, int measureNumber) {
		
		this.measureNumber=measureNumber;
		
		this.notelist = new ArrayList<Note>();
		
		Measure.divisions = 2; // Change this?
		
		this.clefLine=clefLine;		
		
		this.timeBeats = timeBeats;
		this.timeBeatType = timeBeatType;
	}
	
	// Create measure based on existing note list
	public Measure(ArrayList<Note> notelist) {
		this.notelist = notelist;
	}
	public int getMeasureNumber() {
		return measureNumber;
	}
	public ArrayList<Note> getNotelist() {
		return notelist;
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
