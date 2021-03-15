package TAB2MXL;

public class DrumNote extends Note {
	public String notehead;
	public String instrumentId;
	
	
	public DrumNote(String step, int octave, int fret, int string) {
		super(step, octave, fret, string);
	}
	
	public DrumNote(String step, int octave, String id) {
		this.step = step;
		this.octave = octave;
		this.instrumentId = id;
	}
	
	public void setNotehead(String notehead) {
		this.notehead = notehead;
	}
	
	public void setInstrument(String id) {
		this.instrumentId = id;
	}

	public String getNotehead() {
		return notehead;
	}

	public String getInstrumentId() {
		return instrumentId;
	}

}
