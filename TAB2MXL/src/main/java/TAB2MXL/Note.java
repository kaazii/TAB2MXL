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
}
