package TAB2MXL;

public class Note {

	public int duration;
	public int octave; 
	public char step;
	
	public int voice; 
	public String type;
	
	public int string;
	public int fret;
	
	public Note(char step, int duration, int octave) {
		this.step = step;
		this.duration = duration;
		this.octave = octave; 
	}
}
