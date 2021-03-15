package TAB2MXL;

public class XMLUtility {

	// Default values (Guitar)
	String instrument = "GUITAR";
	String pitchTagString = "pitch";
	String stepTagString = "step";
	String octaveTagString = "octave";
	
	boolean includeStaffDetails = true;
	boolean includeInstrumentInNote = false;
	boolean includeNoteHead = false;
	boolean includeNotations = true;
	boolean includeBeams = false;
	
	// DRUMS or GUITAR or BASS
	public XMLUtility(String instrument) {
		this.instrument = instrument;
		
		if (instrument.equals("DRUMS")) {
			this.includeStaffDetails = false;
			this.stepTagString = "display-step";
			this.octaveTagString = "display-octave";
			this.pitchTagString = "unpitched";
			this.includeInstrumentInNote = true;
			this.includeNoteHead = true;
			this.includeNotations = false;
			this.includeBeams = true;
		}
	}

}
