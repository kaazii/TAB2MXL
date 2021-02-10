package TAB2MXL;

public class Note {

	public int duration;
	public int octave; 
	public String step;
	
	public int voice; 
	public String type;
	
	public int string;
	public int fret;
	
	public Note(String step, int duration, int octave) {
		this.step = step;
		this.duration = duration;
		this.octave = octave; 
	}
	
	public Note() {}
	
	public void setOctave(int octave) {
		this.octave = octave;
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
}
