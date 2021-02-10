package tester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
	    }
	@Test
	public void testConstructor(){
		Measure measure=new Measure(1,2,3,4);
		assertEquals(4,measure.getMeasureNumber());
		assertEquals(1,measure.getClefLine());
		
	}
	@SuppressWarnings({ "static-access", "unlikely-arg-type" })
	@Test
	public void testStringParserUtility(){
		StringParserUtility stringparserutility=new StringParserUtility();
		assertTrue(stringparserutility.stringParse("E").equals("This is a Guitar tab."));
		assertTrue(stringparserutility.stringParse("C").equals("This is a Drum tab."));
		assertTrue(stringparserutility.stringParse("G").equals("This is a Bass tab."));
		//equals("This is a Guitar tab."==stringparserutility.stringParse("E"));
		//equals("This is a Drum tab."==stringparserutility.stringParse("C"));
		//equals("This is a Bass tab."==stringparserutility.stringParse("R"));
		
	}
	/*
	@SuppressWarnings("static-access")
	@Test
	public void testNoteUtility(){
		NoteUtility noteutility=new NoteUtility();
		Note note=new Note("E",4,0,1);
		assertSame(noteutility.guitarNote[0][0],note);		
	}
	*/
	
}
