package TAB2MXL;

import java.util.ArrayList;

public class DrumNote extends Note {
	public String notehead;
	public String instrumentId;
	public String stem;
	public ArrayList<Beam> beamList;
	
	
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
	
	public void setBeamList(ArrayList<Beam> beamList) {
		this.beamList = beamList;
	}
	
	public void addBeam(Beam b) {
		this.beamList.add(b);
	}

}
