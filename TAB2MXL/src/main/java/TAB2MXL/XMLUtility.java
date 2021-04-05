package TAB2MXL;

public class XMLUtility {

	// Default values (Guitar)
	String instrument = "GUITAR";
	String pitchTagString = "pitch";
	String stepTagString = "step";
	String octaveTagString = "octave";
	String clefSign = "TAB";
	String clefLine = "5";

	boolean includeStaffDetails = true;
	boolean includeInstrumentInNote = false;
	boolean includeNoteHead = false;
	boolean includeNotations = true;
	boolean includeBeams = false;

	// DRUMS or GUITAR or BASS
	public XMLUtility(String instrument) {
		this.instrument = instrument;

		if (instrument.equals("DRUMS")) {
			instrument = "DRUMS";
			this.includeStaffDetails = false;
			this.stepTagString = "display-step";
			this.octaveTagString = "display-octave";
			this.pitchTagString = "unpitched";
			this.includeInstrumentInNote = true;
			this.includeNoteHead = true;
			this.includeNotations = false;
			this.includeBeams = true;
			this.clefSign = "percussion";
			this.clefLine = "2";
		} else if (instrument.equals("BASS")) {

		} else {
			instrument = "GUITAR";
			pitchTagString = "pitch";
			stepTagString = "step";
			octaveTagString = "octave";
			clefSign = "TAB";
			clefLine = "5";
			includeStaffDetails = true;
			includeInstrumentInNote = false;
			includeNoteHead = false;
			includeNotations = true;
			includeBeams = false;
		}
	}

}
