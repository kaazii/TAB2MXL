package tester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import TAB2MXL.Measure;
import TAB2MXL.Note;
import TAB2MXL.NoteUtility;
import TAB2MXL.XmlGenerator;
import View.Controller;
import View.Main;
import View.Main2;
import View.MyFrame;
import View.RoundedBorder;
import View.StringParserUtility;

public class TestProject {
	Measure measure;
	Note note;
	NoteUtility noteutility;
	StringParserUtility stringparserutility;
	
	@Before
	public void setUp() throws Exception {
		 measure = new Measure(1, 2, 3, 4);
		 note = new Note("E",4,0,1);
	    }
	@Test
	public void testMeasureConstructor1(){
		Measure measure=new Measure(1,2,3,4);
		assertEquals(4,measure.getMeasureNumber());
		assertEquals(1,measure.getClefLine());
		
	}
	@Test
	public void testMeasureConstructor2(){
		Measure measure=new Measure(1,8,8,333);
		assertEquals(333,measure.getMeasureNumber());
		assertEquals(1,measure.getClefLine());
		
	}
	@SuppressWarnings({ "static-access", "unlikely-arg-type" })
	@Test
	public void testStringParserUtilityGuitar(){
		StringParserUtility stringparserutility=new StringParserUtility();
		assertTrue(stringparserutility.stringParse("E").equals("This is a Guitar tab."));
		//assertTrue(stringparserutility.stringParse("C").equals("This is a Drum tab."));
		//assertTrue(stringparserutility.stringParse("G").equals("This is a Bass tab."));
		//equals("This is a Guitar tab."==stringparserutility.stringParse("E"));
		//equals("This is a Drum tab."==stringparserutility.stringParse("C"));
		//equals("This is a Bass tab."==stringparserutility.stringParse("R"));
		
	}
	@SuppressWarnings("static-access")
	@Test
	public void testStringParserUtilityDrum(){
		StringParserUtility stringparserutility=new StringParserUtility();
		assertTrue(stringparserutility.stringParse("C").equals("This is a Drum tab."));
	}
	@SuppressWarnings("static-access")
	@Test
	public void testStringParserUtilityBass(){
		StringParserUtility stringparserutility=new StringParserUtility();
		assertTrue(stringparserutility.stringParse("G").equals("This is a Bass tab."));
	}
	@SuppressWarnings("static-access")
	@Test
	public void testStringParserUtilityUnknown1(){
		StringParserUtility stringparserutility=new StringParserUtility();
		assertTrue(stringparserutility.stringParse("A").equals("Unknown Instrument."));
	}
	@SuppressWarnings("static-access")
	@Test
	public void testStringParserUtilityUnknown2(){
		StringParserUtility stringparserutility=new StringParserUtility();
		assertTrue(stringparserutility.stringParse("B").equals("Unknown Instrument."));
	}
	@Test
	public void testVoice(){
		Note note=new Note("E",4,0,1);
		assertEquals(1,note.getVoice());	
	}
	@Test
	public void testOctave(){
		Note note=new Note("E",4,0,1);
		assertEquals(4,note.getOctave());	
	}
	@Test
	public void testStep(){
		Note note=new Note("E",4,0,1);
		assertTrue(note.getStep().equals("E"));	
	}
	
	@Test
	public void testDuration() {
		note.setDuration(10);
		assertEquals(10, note.getDuration());
	}
	
	@Test
	public void testVoiceSetter() {
		
		note.setVoice(2);
		assertEquals(2, note.getVoice());
	}
	
	/*@SuppressWarnings("static-access")
	@Test
	public void testNoteUtility(){
		NoteUtility noteutility=new NoteUtility();
		Note note=new Note("E",4,0,1);
		assertSame(noteutility.guitarNote[0][0],note);		
	}
	*/
	@Test
	public void noteListMeasureTest() {
		ArrayList<Note> list = new ArrayList<>();
		list.add(note);
		measure = new Measure(list);
		assertSame(note, measure.getNotelist().get(0));
		
	}
	
	@Test
	public void measureDivisionTest() {
		assertEquals(2, Measure.divisions);
	}
	
	@Test
	public void noteStringTest() {
		assertEquals(1, note.getString());
		note.setString(2);
		assertEquals(2, note.getString());
	}
	
}