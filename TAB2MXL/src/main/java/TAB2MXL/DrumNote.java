package TAB2MXL;

import java.util.ArrayList;

public class DrumNote extends Note {
	public String notehead;
	public String instrumentId;
	public String stem;
	public String instrumentName;
	

	
	public DrumNote(String step, int octave, String id, String instrumentName) {
		this.step = step;
		this.octave = octave;
		this.instrumentId = id;
		this.instrumentName = instrumentName;
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
	
	public void setInstrumentName(String name) {
		this.instrumentName = name;
	}

}
