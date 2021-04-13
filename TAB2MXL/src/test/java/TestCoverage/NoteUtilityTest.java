package TestCoverage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import TAB2MXL.DrumNote;
import TAB2MXL.Note;
import TAB2MXL.NoteUtility;
import View.TuningController;

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
	
	@Test
	void testCounterGetterSetter() {
		noteUtil.setCounter1(1);
		noteUtil.setCounter2(2);
		noteUtil.setCounter3(3);
		noteUtil.setCounter4(4);
		noteUtil.setCounter5(5);
		noteUtil.setCounter6(6);
		noteUtil.setOctave1(4);
		noteUtil.setOctave2(3);
		noteUtil.setOctave3(4);
		noteUtil.setOctave4(5);
		noteUtil.setOctave5(1);
		noteUtil.setOctave6(4);
		assertEquals(1, noteUtil.getCounter1());
		assertEquals(2, noteUtil.getCounter2());
		assertEquals(3, noteUtil.getCounter3());
		assertEquals(4, noteUtil.getCounter4());
		assertEquals(5, noteUtil.getCounter5());
		assertEquals(6, noteUtil.getCounter6());
		assertEquals(4, noteUtil.getOctave1());
		assertEquals(3, noteUtil.getOctave2());
		assertEquals(4, noteUtil.getOctave3());
		assertEquals(5, noteUtil.getOctave4());
		assertEquals(1, noteUtil.getOctave5());
		assertEquals(4, noteUtil.getOctave6());
	}


}
