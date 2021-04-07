package TAB2MXL;

import java.util.ArrayList;

public class Note {

	public Beam beam;
	
	public boolean isChord = false; //true if it's a chord
	public boolean isDotted = false; //true if it's a dotted note
	public boolean isHarmonic = false; //true if it's a harmonic

	public String startOrStop = ""; //either "start" or "stop"
	public String complexType = ""; // h, s or p
	
	public int complexTypeNumber;
	
	public int column; //for chords
	
	public int duration;
	
	public float floatDuration;
	
	public int octave; 
	
	public String step;
	public int voice = 1; 
	public String type;
	
	public int string;
	public int fret;
	
	public Note(String step, int octave, int fret, int string) {
		this.step = step;
		this.octave = octave; 
		this.fret = fret;
		this.string = string;
	}
	
	public Note() {}
	//*************************setter**************************
	
	public void setOctave(int octave) {
		this.octave = octave;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public void setVoice(int voice) {
		this.voice = voice;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setString(int string) {
		this.string = string;
	}

	public void setFret(int fret) {
		this.fret = fret;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	//******************getter**********************
	public int getOctave() {
		return octave;
	}

	public String getStep() {
		return step;
	}

	public int getVoice() {
		return voice;
	}

	public String getType() {
		return type;
	}

	public int getString() {
		return string;
	}

	public int getFret() {
		return fret;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public float getFloatDuration() {
		return this.floatDuration;
	}
	
	public void start() {
		this.startOrStop = "start";
	}
	
	public void stop() {
		this.startOrStop = "stop";
	}
}