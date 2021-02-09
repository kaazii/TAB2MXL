package TAB2MXL;

import java.util.ArrayList;

public class Measure {
	
	public int measureNumber;
	
	public ArrayList<Note> notelist;
	public static int divisions;
		
	public int keyFifths=0;//always 0
	
	public int timeBeats;// user input 	
	public int timeBeatType;// user input
	
	public char clefSign;
	public int clefLine;

	// Create a new measure
	public Measure(char clefSign, int clefLine, int timeBeats, int timeBeatType, int measureNumber) {
		
		this.measureNumber=measureNumber;
		
		this.notelist = new ArrayList<Note>();
		
		Measure.divisions = 2; // Change this?
		
		this.clefLine=clefLine;
		this.clefSign=clefSign;
		
		
		this.timeBeats = timeBeats;
		this.timeBeatType = timeBeatType;
	}
	
	// Create measure based on existing note list
	public Measure(ArrayList<Note> notelist) {
		this.notelist = notelist;
	}

}
