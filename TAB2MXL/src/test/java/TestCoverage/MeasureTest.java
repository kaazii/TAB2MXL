package TestCoverage;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import TAB2MXL.Measure;
import TAB2MXL.Note;

class MeasureTest {
	
	Measure measure;
	@BeforeEach
	void setUp() throws Exception {
		measure = new Measure(5, 2);
	}

	@Test
	void testGetMeasureNumber() {
		assertEquals(2, measure.getMeasureNumber());
		assertEquals(2, measure.gettimeBeatType());
		assertEquals(2, measure.getTimeBeats());
	}
	
	@Test
	void testNoteList() {
		Note note = new Note("E",4,0,1);
		measure.noteList.add(note);
		List<Note> notes = measure.getNoteList();
		assertEquals(notes.get(0), note);
		assertEquals(notes.size(), 1);
		
	}
	
	@Test
	void testGetClefLine() {
		assertEquals(5, measure.getClefLine());
		
	}
	
	@Test
	void testGetDivisions() {
		measure = new Measure(2);
		assertEquals(2, measure.getDivision());
	}
	
	

}
