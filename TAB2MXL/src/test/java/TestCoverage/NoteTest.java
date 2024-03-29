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
	
	@Test
	public void testSetOctave() {
		note.setOctave(1);
		assertEquals(1, note.getOctave());
	}
	
	@Test
	public void testSetColumn() {
		note.setColumn(5);
		assertEquals(5, note.column);
	}
	
	@Test
	public void testSetStep() {
		note.setStep("A");
		assertEquals("A", note.getStep());
	}
	
	@Test
	public void testSetString() {
		note.setString(2);
		assertEquals(2, note.getString());
	}
	
	@Test
	public void testSetFret() {
		note.setFret(5);
		assertEquals(5, note.getFret());
	}

	@Test
	public void testSetType() {
		note.setType("half");
		assertEquals("half", note.getType());
	}
	
	@Test
	public void testAlter() {
		note.setAlter(1);
		assertEquals(1, note.getAlter());
	}
	
	@Test
	public void testConstructorAlter() {
		note = new Note("E",0);
		assertEquals("E",note.getStep());
		assertEquals(0,note.getAlter());
	}
	
	@Test
	public void testConstructorAlterOctave() {
		note = new Note("E",0,4);
		assertEquals("E",note.getStep());
		assertEquals(0,note.getAlter());
		assertEquals(4, note.getOctave());
	}


}
