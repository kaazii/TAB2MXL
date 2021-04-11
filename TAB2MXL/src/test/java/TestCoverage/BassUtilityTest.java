package TestCoverage;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import TAB2MXL.BassNoteUtility;
import TAB2MXL.Note;

class BassUtilityTest {
	BassNoteUtility bassUtil;
	@BeforeEach
	void setUp() throws Exception {
		bassUtil = new BassNoteUtility();
		bassUtil.initialise();
	}

	@Test
	void testInitialize() {
		//BassNote[0][0] = new Note("G", 2, 0, 1);
		Note note = bassUtil.BassNote[0][0];
		assertEquals("G",note.getStep());
		assertEquals(2, note.getOctave());
		assertEquals(0, note.getFret());
		assertEquals(1, note.getString());
	}

}
