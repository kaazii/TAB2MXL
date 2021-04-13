package TestCoverage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import TAB2MXL.BassNoteUtility;
import TAB2MXL.Measure;
import TAB2MXL.Note;
import TAB2MXL.NoteUtility;
import TAB2MXL.XmlGenerator;
import View.Controller;

class XmlGeneratorTest {

	@BeforeEach
	void setUp() throws Exception {
		Controller.TITLE = "";
		Controller.COMPOSER = "";
	}

	@Test
	void testException() {
		ArrayList<Measure> measureList = new ArrayList<>();

		assertThrows(Exception.class, () -> {
			XmlGenerator.Generate(measureList, "GUITAR");
		});
	}

	@Test
	void testSimpleTranslation() throws Exception {
		ArrayList<Measure> measureList = new ArrayList<>();
		Measure measure = new Measure(1, 2);
		measureList.add(measure);
		Note note = new Note("E", 4, 0, 1);
		note.setColumn(1);
		note.setType("whole");
		note.setVoice(1);
		note.setDuration(1);
		measure.noteList.add(note);
		String result = XmlGenerator.Generate(measureList, "GUITAR");
		String correct = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
				+ "<!DOCTYPE score-partwise PUBLIC \"-//Recordare//DTD MusicXML 3.1 Partwise//EN\" \"http://www.musicxml.org/dtds/partwise.dtd\">\n"
				+ "<score-partwise version=\"3.1\">\n" + "    <part-list>\n" + "        <score-part id=\"P1\">\n"
				+ "            <part-name>GUITAR</part-name>\n" + "        </score-part>\n" + "    </part-list>\n"
				+ "    <part id=\"P1\">\n" + "        <measure number=\"1\">\n" + "            <attributes>\n"
				+ "                <divisions>0</divisions>\n" + "                <key>\n"
				+ "                    <fifths>0</fifths>\n" + "                </key>\n" + "                <time>\n"
				+ "                    <beats>0</beats>\n" + "                    <beat-type>4</beat-type>\n"
				+ "                </time>\n" + "                <clef>\n" + "                    <sign>TAB</sign>\n"
				+ "                    <line>5</line>\n" + "                </clef>\n"
				+ "                <staff-details>\n" + "                    <staff-lines>6</staff-lines>\n"
				+ "                    <staff-tuning line=\"1\">\n"
				+ "                        <tuning-step>E</tuning-step>\n"
				+ "                        <tuning-octave>4</tuning-octave>\n" + "                    </staff-tuning>\n"
				+ "                    <staff-tuning line=\"2\">\n"
				+ "                        <tuning-step>B</tuning-step>\n"
				+ "                        <tuning-octave>3</tuning-octave>\n" + "                    </staff-tuning>\n"
				+ "                    <staff-tuning line=\"3\">\n"
				+ "                        <tuning-step>G</tuning-step>\n"
				+ "                        <tuning-octave>3</tuning-octave>\n" + "                    </staff-tuning>\n"
				+ "                    <staff-tuning line=\"4\">\n"
				+ "                        <tuning-step>D</tuning-step>\n"
				+ "                        <tuning-octave>3</tuning-octave>\n" + "                    </staff-tuning>\n"
				+ "                    <staff-tuning line=\"5\">\n"
				+ "                        <tuning-step>A</tuning-step>\n"
				+ "                        <tuning-octave>2</tuning-octave>\n" + "                    </staff-tuning>\n"
				+ "                    <staff-tuning line=\"6\">\n"
				+ "                        <tuning-step>E</tuning-step>\n"
				+ "                        <tuning-octave>2</tuning-octave>\n" + "                    </staff-tuning>\n"
				+ "                </staff-details>\n" + "            </attributes>\n" + "            <note>\n"
				+ "                <pitch>\n" + "                    <step>E</step>\n"
				+ "                    <octave>4</octave>\n" + "                </pitch>\n"
				+ "                <duration>1</duration>\n" + "                <voice>1</voice>\n"
				+ "                <type>whole</type>\n" + "                <notations>\n"
				+ "                    <technical>\n" + "                        <string>1</string>\n"
				+ "                        <fret>0</fret>\n" + "                    </technical>\n"
				+ "                </notations>\n" + "            </note>\n" + "        </measure>\n" + "    </part>\n"
				+ "</score-partwise>\n";
		assertEquals(correct, result);
	}

	@Test
	void testSimpleDrum() throws Exception {
		ArrayList<Measure> measureList = new ArrayList<>();
		Measure measure = new Measure(1, 2);
		measureList.add(measure);
		NoteUtility nu = new NoteUtility();
		nu.initializeDrum();

		Note note = nu.drumNotes.get("BD");
		note.type = "whole";
		note.duration = 1;

		measure.noteList.add(note);

		String result = XmlGenerator.Generate(measureList, "DRUMS");

		assertEquals(result, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
				+ "<!DOCTYPE score-partwise PUBLIC \"-//Recordare//DTD MusicXML 3.1 Partwise//EN\" \"http://www.musicxml.org/dtds/partwise.dtd\">\n"
				+ "<score-partwise version=\"3.1\">\n" + "    <part-list>\n" + "        <score-part id=\"P1\">\n"
				+ "            <part-name>DRUMS</part-name>\n" + "            <score-instrument id=\"P1-I36\">\n"
				+ "                <instrument-name>Bass Drum 1</instrument-name>\n"
				+ "            </score-instrument>\n" + "        </score-part>\n" + "    </part-list>\n"
				+ "    <part id=\"P1\">\n" + "        <measure number=\"1\">\n" + "            <attributes>\n"
				+ "                <divisions>0</divisions>\n" + "                <key>\n"
				+ "                    <fifths>0</fifths>\n" + "                </key>\n" + "                <time>\n"
				+ "                    <beats>0</beats>\n" + "                    <beat-type>4</beat-type>\n"
				+ "                </time>\n" + "                <clef>\n"
				+ "                    <sign>percussion</sign>\n" + "                    <line>2</line>\n"
				+ "                </clef>\n" + "            </attributes>\n" + "            <note>\n"
				+ "                <unpitched>\n" + "                    <display-step>F</display-step>\n"
				+ "                    <display-octave>4</display-octave>\n" + "                </unpitched>\n"
				+ "                <duration>1</duration>\n" + "                <instrument id=\"P1-I36\"/>\n"
				+ "                <voice>1</voice>\n" + "                <type>whole</type>\n"
				+ "            </note>\n" + "        </measure>\n" + "    </part>\n" + "</score-partwise>\n");

	}

	@Test
	void testSimpleBass() throws Exception {
		ArrayList<Measure> measureList = new ArrayList<>();
		Measure measure = new Measure(1, 2);
		measureList.add(measure);
		BassNoteUtility nu = new BassNoteUtility();
		nu.initialise();

		Note note = nu.BassNote[0][0];
		note.isChord = true;
		note.type = "whole";
		note.duration = 1;

		measure.noteList.add(note);
		measure.clefLine = 2;
		measure.repeatEnd = true;
		measure.repeatStart = true;
		measure.repeats = 4;

		String result = XmlGenerator.Generate(measureList, "BASS");

		assertEquals(result, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
				+ "<!DOCTYPE score-partwise PUBLIC \"-//Recordare//DTD MusicXML 3.1 Partwise//EN\" \"http://www.musicxml.org/dtds/partwise.dtd\">\n"
				+ "<score-partwise version=\"3.1\">\n" + "    <part-list>\n" + "        <score-part id=\"P1\">\n"
				+ "            <part-name>BASS</part-name>\n" + "        </score-part>\n" + "    </part-list>\n"
				+ "    <part id=\"P1\">\n" + "        <measure number=\"1\">\n" + "            <attributes>\n"
				+ "                <divisions>0</divisions>\n" + "                <key>\n"
				+ "                    <fifths>0</fifths>\n" + "                </key>\n" + "                <time>\n"
				+ "                    <beats>0</beats>\n" + "                    <beat-type>4</beat-type>\n"
				+ "                </time>\n" + "                <clef>\n" + "                    <sign>TAB</sign>\n"
				+ "                    <line>5</line>\n" + "                </clef>\n" + "            </attributes>\n"
				+ "            <barline location=\"left\">\n" + "                <bar-style>heavy-light</bar-style>\n"
				+ "                <repeat direction=\"forward\"/>\n" + "            </barline>\n"
				+ "            <direction placement=\"above\">\n" + "                <direction-type>\n"
				+ "                    <words relative-x=\"256.17\" relative-y=\"16.01\">Repeat 4 times</words>\n"
				+ "                </direction-type>\n" + "            </direction>\n" + "            <note>\n"
				+ "                <chord/>\n" + "                <pitch>\n" + "                    <step>G</step>\n"
				+ "                    <octave>2</octave>\n" + "                </pitch>\n"
				+ "                <duration>1</duration>\n" + "                <voice>1</voice>\n"
				+ "                <type>whole</type>\n" + "                <notations>\n"
				+ "                    <technical>\n" + "                        <string>1</string>\n"
				+ "                        <fret>0</fret>\n" + "                    </technical>\n"
				+ "                </notations>\n" + "            </note>\n"
				+ "            <barline location=\"right\">\n" + "                <bar-style>heavy-light</bar-style>\n"
				+ "                <repeat direction=\"backward\"/>\n" + "            </barline>\n"
				+ "        </measure>\n" + "    </part>\n" + "</score-partwise>\n" + "");
	}

	@Test
	void testTitleComposer() throws Exception {
		ArrayList<Measure> measureList = new ArrayList<>();
		Controller.TITLE = "Song";
		Controller.COMPOSER = "Mozart";
		Measure measure = new Measure(1, 2);
		measureList.add(measure);
		Note note = new Note("E", 4, 0, 1);
		note.setColumn(1);
		note.setType("whole");
		note.setVoice(1);
		note.setDuration(1);
		measure.noteList.add(note);
		String result = XmlGenerator.Generate(measureList, "GUITAR");
		assertEquals(result, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
				+ "<!DOCTYPE score-partwise PUBLIC \"-//Recordare//DTD MusicXML 3.1 Partwise//EN\" \"http://www.musicxml.org/dtds/partwise.dtd\">\n"
				+ "<score-partwise version=\"3.1\">\n" + "    <work>\n" + "        <work-title>Song</work-title>\n"
				+ "    </work>\n" + "    <identification>\n" + "        <creator type=\"composer\">Mozart</creator>\n"
				+ "    </identification>\n" + "    <part-list>\n" + "        <score-part id=\"P1\">\n"
				+ "            <part-name>GUITAR</part-name>\n" + "        </score-part>\n" + "    </part-list>\n"
				+ "    <part id=\"P1\">\n" + "        <measure number=\"1\">\n" + "            <attributes>\n"
				+ "                <divisions>0</divisions>\n" + "                <key>\n"
				+ "                    <fifths>0</fifths>\n" + "                </key>\n" + "                <time>\n"
				+ "                    <beats>0</beats>\n" + "                    <beat-type>4</beat-type>\n"
				+ "                </time>\n" + "                <clef>\n" + "                    <sign>TAB</sign>\n"
				+ "                    <line>5</line>\n" + "                </clef>\n"
				+ "                <staff-details>\n" + "                    <staff-lines>6</staff-lines>\n"
				+ "                    <staff-tuning line=\"1\">\n"
				+ "                        <tuning-step>E</tuning-step>\n"
				+ "                        <tuning-octave>4</tuning-octave>\n" + "                    </staff-tuning>\n"
				+ "                    <staff-tuning line=\"2\">\n"
				+ "                        <tuning-step>B</tuning-step>\n"
				+ "                        <tuning-octave>3</tuning-octave>\n" + "                    </staff-tuning>\n"
				+ "                    <staff-tuning line=\"3\">\n"
				+ "                        <tuning-step>G</tuning-step>\n"
				+ "                        <tuning-octave>3</tuning-octave>\n" + "                    </staff-tuning>\n"
				+ "                    <staff-tuning line=\"4\">\n"
				+ "                        <tuning-step>D</tuning-step>\n"
				+ "                        <tuning-octave>3</tuning-octave>\n" + "                    </staff-tuning>\n"
				+ "                    <staff-tuning line=\"5\">\n"
				+ "                        <tuning-step>A</tuning-step>\n"
				+ "                        <tuning-octave>2</tuning-octave>\n" + "                    </staff-tuning>\n"
				+ "                    <staff-tuning line=\"6\">\n"
				+ "                        <tuning-step>E</tuning-step>\n"
				+ "                        <tuning-octave>2</tuning-octave>\n" + "                    </staff-tuning>\n"
				+ "                </staff-details>\n" + "            </attributes>\n" + "            <note>\n"
				+ "                <pitch>\n" + "                    <step>E</step>\n"
				+ "                    <octave>4</octave>\n" + "                </pitch>\n"
				+ "                <duration>1</duration>\n" + "                <voice>1</voice>\n"
				+ "                <type>whole</type>\n" + "                <notations>\n"
				+ "                    <technical>\n" + "                        <string>1</string>\n"
				+ "                        <fret>0</fret>\n" + "                    </technical>\n"
				+ "                </notations>\n" + "            </note>\n" + "        </measure>\n" + "    </part>\n"
				+ "</score-partwise>\n" + "");
	}

}
