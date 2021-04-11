package TestCoverage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import TAB2MXL.DrumNote;
import TAB2MXL.Note;
import TAB2MXL.NoteUtility;

class NoteUtilityTest {
	NoteUtility noteUtil;
	@BeforeEach
	void setUp() throws Exception {
		noteUtil= new NoteUtility();
		noteUtil.initialise();
		noteUtil.initializeDrum();
	}

	@Test
	void testDrumInitialize() {
		DrumNote note = (DrumNote) noteUtil.drumNotes.get("BD");
		assertEquals("F", note.getStep());
		assertEquals(4,note.getOctave());
		assertEquals("P1-I36",note.getInstrumentId());
		assertEquals("Bass Drum 1",note.instrumentName);
	}
	
	@Test
	void testGuitarInitialize() {
		Note note = noteUtil.guitarNote[0][0];
		assertEquals("E",note.getStep());
		assertEquals(4, note.getOctave());
		assertEquals(0, note.getFret());
		assertEquals(1, note.getString());
	}
	
	@Test
	void testNoteType() {
		Note note = noteUtil.guitarNote[0][0];
		String type = NoteUtility.getNoteType(1,note);
		
		assertEquals("whole", type);
	}

}
