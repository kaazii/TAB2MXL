package TestCoverage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import TAB2MXL.Note;

class NoteTest {

	Note note;
	@BeforeEach
	public void setUp() throws Exception {
		note = new Note("E",4,0,1);
	}

	
	@Test
	public void noteTestVoice(){
		assertEquals(1,note.getVoice());	
	}
	@Test
	public void noteTestOctave(){
		assertEquals(4,note.getOctave());	
	}
	@Test
	public void noteTestStep(){
		assertTrue(note.getStep().equals("E"));	
	}
	
	@Test
	public void noteTestDuration() {
		note.setDuration(10);
		assertEquals(10, note.getDuration());
	}
	
	@Test
	public void noteTestVoiceSetter() {
		note.setVoice(2);
		assertEquals(2, note.getVoice());
	}


}
