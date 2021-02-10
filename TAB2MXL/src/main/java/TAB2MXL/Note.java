package TAB2MXL;

public class Note {

	public int duration;
	public int octave; 
	public char step;
	
	public int voice=1; 
	public String type;
	
	public int string;
	public int fret;
	
	public Note(char step, int octave, int fret, int string) {
		this.step = step;
		this.octave = octave; 
		this.fret=fret;
		this.string=string;
	}
}
