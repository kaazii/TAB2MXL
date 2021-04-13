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
	
	@Test
	void testCounterGetterSetter() {
		bassUtil.setCounter1(1);
		bassUtil.setCounter2(2);
		bassUtil.setCounter3(3);
		bassUtil.setCounter4(4);
		bassUtil.setOctave1(4);
		bassUtil.setOctave2(3);
		bassUtil.setOctave3(4);
		bassUtil.setOctave4(5);
		assertEquals(1, bassUtil.getCounter1());
		assertEquals(2, bassUtil.getCounter2());
		assertEquals(3, bassUtil.getCounter3());
		assertEquals(4, bassUtil.getCounter4());
		assertEquals(4, bassUtil.getOctave1());
		assertEquals(3, bassUtil.getOctave2());
		assertEquals(4, bassUtil.getOctave3());
		assertEquals(5, bassUtil.getOctave4());
	}

}
