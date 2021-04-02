package TAB2MXL;

public class Note {

	public boolean isChord = false; //true if it's a chord
	public int column; //for chords
	
	public int duration;
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
	
}
