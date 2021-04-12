package TAB2MXL;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import View.Controller;
import View.StringParserUtility;
import View.TuningController;

public class XmlGenerator {

	public static final String PART_NAME = " ";
	private static Document doc;
	private static String fifths = String.valueOf(0);

	private static XMLUtility xutil = new XMLUtility("GUITAR"); // Guitar by default

	public static String Generate(ArrayList<Measure> measureList, String instrumentString) throws Exception {

		// Check if measure list is empty
		if (measureList.isEmpty()) {
			throw new Exception("Measure List passed into XML generator is empty");
		}

		xutil = new XMLUtility(instrumentString);

		String xmlString = "";

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.newDocument();

			// root element
			Element rootElement = doc.createElement("score-partwise");
			doc.appendChild(rootElement);

			// Add "Version 3.1" to the score-partwise attribute
			Attr attr = doc.createAttribute("version");
			attr.setValue("3.1");
			rootElement.setAttributeNode(attr);

			if (Controller.TITLE != "") {
				// <work>
				Element work = doc.createElement("work");
				rootElement.appendChild(work);

				// <work-title>
				Element e = doc.createElement("work-title");
				e.appendChild(doc.createTextNode(Controller.TITLE));
				work.appendChild(e);
			} else {
				System.out.println("No title detected");
			}

			if (Controller.COMPOSER != "") {
				// <identification>
				Element identification = doc.createElement("identification");
				rootElement.appendChild(identification);

				// <creator>
				Element e = doc.createElement("creator");

				attr = doc.createAttribute("type");
				attr.setValue("composer");
				e.setAttributeNode(attr);

				e.appendChild(doc.createTextNode(Controller.COMPOSER));

				identification.appendChild(e);
			} else {
				System.out.println("No composer detected");
			}

			// part-list element. Create only one part for now
			Element partList = doc.createElement("part-list");
			rootElement.appendChild(partList);

			// score-part element
			Element scorePart = doc.createElement("score-part");
			partList.appendChild(scorePart);

			// Add ID attribute
			attr = doc.createAttribute("id");
			attr.setValue("P1");
			scorePart.setAttributeNode(attr);

			// Add part name
			Element partName = doc.createElement("part-name");
			scorePart.appendChild(partName);

			if (XmlGenerator.xutil.instrument.equals("DRUMS")) {

				// Add drum instrument list
				Hashtable<String, String> instrumentList = generateDrumInstruments(measureList);
				for (String id : instrumentList.keySet()) {
					// <score-instrument>
					Element scoreInstrument = doc.createElement("score-instrument");
					scorePart.appendChild(scoreInstrument);

					// Add ID attribute
					attr = doc.createAttribute("id");
					attr.setValue(id);
					scoreInstrument.setAttributeNode(attr);

					// <instrument-name>
					Element instrumentName = doc.createElement("instrument-name");
					scoreInstrument.appendChild(instrumentName);
					instrumentName.appendChild(doc.createTextNode(instrumentList.get(id)));
				}
			} else if (instrumentString == "BASS") {

			}
			// Set part name as the instrument name
			partName.appendChild(doc.createTextNode(xutil.instrument));

			// Create part 1
			Element part1 = doc.createElement("part");
			attr = doc.createAttribute("id");
			attr.setValue("P1");
			part1.setAttributeNode(attr);
			rootElement.appendChild(part1);

			addMeasures(part1, measureList);

			// write the content into a string writer then save to a variable
			// Source:
			// https://stackoverflow.com/questions/23520208/how-to-create-xml-file-with-specific-structure-in-java
			// Answer by Tamil veera Cholan
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			DOMImplementation domImpl = dBuilder.getDOMImplementation();
			DocumentType doctype = domImpl.createDocumentType("doctype", "-//Recordare//DTD MusicXML 3.1 Partwise//EN",
					"http://www.musicxml.org/dtds/partwise.dtd");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
			DOMSource source = new DOMSource(doc);

			// Create a new stringwriter
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			transformer.transform(source, result);

			xmlString = sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return xmlString;
	}

	private static Hashtable<String, String> generateDrumInstruments(ArrayList<Measure> measureList) {
		Measure m = measureList.get(0);
		ArrayList<Note> nl = m.getNoteList();

		Hashtable<String, String> ht = new Hashtable<>();
		for (Note note : nl) {
			DrumNote dn = (DrumNote) note;

			if (!ht.containsKey(dn.instrumentId)) {
				ht.put(dn.instrumentId, dn.instrumentName);
			}
		}
		return ht;
	}

	private static void addMeasures(Element partElement, ArrayList<Measure> measureList) {
		// Initial, unchanging values
		int measureNum = 1;

		// Iterate through Measures list
		for (Measure m : measureList) {
			// Create measure tag and add number attribute as well as it's value
			// <measure number="...">
			Element measureElem = doc.createElement("measure");
			Attr attr = doc.createAttribute("number");
			attr.setValue(String.valueOf(measureNum));
			measureElem.setAttributeNode(attr);
			partElement.appendChild(measureElem);

			if (measureNum == 1) {
				// <attributes>
				Element measureAttribute = doc.createElement("attributes");
				measureElem.appendChild(measureAttribute);

				// -<divisions>
				Element e = doc.createElement("divisions");
				e.appendChild(doc.createTextNode(String.valueOf(m.divisions)));
				measureAttribute.appendChild(e);

				// -<Key>
				Element key = doc.createElement("key");
				measureAttribute.appendChild(key);

				// --<fifths>
				e = doc.createElement("fifths");
				e.appendChild(doc.createTextNode(XmlGenerator.fifths));
				key.appendChild(e);

				// -<time>
				Element time = doc.createElement("time");
				measureAttribute.appendChild(time);

				// --<beats>
				e = doc.createElement("beats");
				e.appendChild(doc.createTextNode(String.valueOf(m.timeBeats)));
				time.appendChild(e);

				// --<beat-type>
				e = doc.createElement("beat-type");
				e.appendChild(doc.createTextNode(String.valueOf(m.timeBeatType)));
				time.appendChild(e);

				// -<clef>
				Element clef = doc.createElement("clef");
				measureAttribute.appendChild(clef);

				// --<sign>
				e = doc.createElement("sign");
				e.appendChild(doc.createTextNode(xutil.clefSign));
				clef.appendChild(e);

				// --<line>
				e = doc.createElement("line");
				e.appendChild(doc.createTextNode(xutil.clefLine));
				clef.appendChild(e);

				// -<staff-details>
				if (xutil.includeStaffDetails) {
					Element staffDetails = doc.createElement("staff-details");
					if (xutil.instrument == "GUITAR") {
						addStaffDetails(staffDetails, xutil.instrument);
						measureAttribute.appendChild(staffDetails);
					}
				}
			}

			// Barline logic for repeats - LEFT BARLINE
			if (m.repeatStart && m.repeats > 0) {
				addBarline(measureElem, "left", m.repeats);
			}

			measureNum++;
			// TODO

			// Add notes
			for (Note n : m.noteList) {

				addNote(n, measureElem, false, "", "");
			}

			// Handle grace notes
			if (m.graceNotes.size() != 0) {
				for (GraceNote gn : m.graceNotes) {
					int noteIndex = 0;
					for (Note n : gn.noteList) {
						// TODO GRACE NOTE SHOULD NOT BE STATIC
						String letter = gn.complexTypeList[noteIndex];

						if (letter.equals("g")) {
							// grace note
							// start note[index] with letter[index + 1]
							addNote(n, measureElem, true, gn.complexTypeList[noteIndex + 1], "");

						} else if (noteIndex == gn.noteList.size() - 1) {
							// last element
							// Stop note[index] with letter[index]

							addNote(n, measureElem, false, "", gn.complexTypeList[noteIndex]);

						} else {
							// middle element
							// stop note[index] with letter[index]
							// start note[index] with letter[index + 1]

							addNote(n, measureElem, false, gn.complexTypeList[noteIndex + 1],
									gn.complexTypeList[noteIndex]);
						}
						noteIndex++;
					}
				}
			}

			// right Barline
			if (m.repeatEnd && m.repeats > 0) {
				addBarline(measureElem, "right", m.repeats);
			} else if (measureNum == measureList.size() - 1) { // ending barline
				Element barline = doc.createElement("barline");
				attr = doc.createAttribute("location");
				attr.setValue("right");
				barline.setAttributeNode(attr);
				measureElem.appendChild(barline);

				// <bar-style>
				Element e = doc.createElement("bar-style");
				e.appendChild(doc.createTextNode("light-heavy"));
				barline.appendChild(e);
			}
		}
	}

	private static void addNote(Note n, Element measureElem, boolean isGrace, String startType, String endType) {
		Attr attr;
		Element e;

		// <note>
		Element note = doc.createElement("note");
		measureElem.appendChild(note);

		if (isGrace) {
			e = doc.createElement("grace");
			note.appendChild(e);
		}

		if (n.isChord) {
			// -<chord>
			Element chord = doc.createElement("chord");
			note.appendChild(chord);
		}

		// -<pitch>
		Element pitch = doc.createElement(xutil.pitchTagString);
		note.appendChild(pitch);

		// --<step>
		e = doc.createElement(xutil.stepTagString);
		e.appendChild(doc.createTextNode(n.step));
		pitch.appendChild(e);

		if (n.getAlter() != 0) {
			// --<alter>
			e = doc.createElement("alter");
			e.appendChild(doc.createTextNode(String.valueOf(n.getAlter())));
			pitch.appendChild(e);
		}

		// --<octave>
		e = doc.createElement(xutil.octaveTagString);
		e.appendChild(doc.createTextNode(String.valueOf(n.octave)));
		pitch.appendChild(e);

		// -<duration>
		e = doc.createElement("duration");
		e.appendChild(doc.createTextNode(String.valueOf(n.duration)));
		note.appendChild(e);

		// -<instrument> if it's drums
		if (xutil.includeInstrumentInNote) {
			DrumNote dn = (DrumNote) n;
			e = doc.createElement("instrument");
			attr = doc.createAttribute("id");
			attr.setValue(dn.instrumentId);
			e.setAttributeNode(attr);
			note.appendChild(e);
		}

		// -<voice>
		e = doc.createElement("voice");
		e.appendChild(doc.createTextNode(String.valueOf(n.voice)));
		note.appendChild(e);

		// -<type>
		e = doc.createElement("type");
		e.appendChild(doc.createTextNode(n.type));
		note.appendChild(e);
		// -</dot>
		if (n.isDotted) {
			e = doc.createElement("dot");
			note.appendChild(e);
		}
		// stem if it's drums

		if (xutil.includeNoteHead) {
			DrumNote dn = (DrumNote) n;
			if (dn.stem != null) {
				e = doc.createElement("stem");
				e.appendChild(doc.createTextNode(dn.stem));
				note.appendChild(e);
			}

		}

		// -<notehead> if it's drums
		if (xutil.includeNoteHead) {
			DrumNote dn = (DrumNote) n;
			if (dn.notehead != null) {
				e = doc.createElement("notehead");
				e.appendChild(doc.createTextNode(dn.notehead));
				note.appendChild(e);
			}

		}

		if (xutil.includeBeams) {
			if (!n.beamList.isEmpty()) {
				for (int i = 0; i < n.beamList.size(); i++) {
					Beam b = n.beamList.get(i);
					e = doc.createElement("beam");
					attr = doc.createAttribute("number");
					attr.setValue(String.valueOf(b.number));
					e.setAttributeNode(attr);
					e.appendChild(doc.createTextNode(b.state));
					note.appendChild(e);
				}
			}
		}

		// -<notations>
		if (xutil.includeNotations) {
			Element notations = doc.createElement("notations");
			note.appendChild(notations);

			// -<technical>
			Element technical = doc.createElement("technical");
			notations.appendChild(technical);

			// <hammer-on> / <pull-off>
			if (n.complexType != "") {
				String complexType = n.complexType.toUpperCase();

				String complexTypeString = getComplexTypeString(complexType);

				if (complexTypeString == "slide") {
					e = doc.createElement(complexTypeString);

					attr = doc.createAttribute("line-type");
					attr.setValue("solid");
					e.setAttributeNode(attr);

					attr = doc.createAttribute("number");
					attr.setValue(String.valueOf(n.complexTypeNumber));
					e.setAttributeNode(attr);

					notations.appendChild(e);
				} else {
					e = doc.createElement(complexTypeString);
					e.appendChild(doc.createTextNode(complexType));

					attr = doc.createAttribute("number");
					attr.setValue(String.valueOf(n.complexTypeNumber));
					e.setAttributeNode(attr);

					attr = doc.createAttribute("type");
					attr.setValue(n.startOrStop);
					e.setAttributeNode(attr);

					technical.appendChild(e);

					// <slur>
					e = doc.createElement("slur");
					attr = doc.createAttribute("number");
					attr.setValue(String.valueOf(n.complexTypeNumber));
					e.setAttributeNode(attr);

					attr = doc.createAttribute("placement");
					attr.setValue("above");
					e.setAttributeNode(attr);

					attr = doc.createAttribute("type");
					attr.setValue(n.startOrStop);
					e.setAttributeNode(attr);

					notations.appendChild(e);
				}
			} else if ((!startType.equals("")) || (!endType.equals(""))) {

				System.out.println("Found a note that's part of a grace");
				if ((!startType.equals("")) && (!endType.equals(""))) {
					String ct1 = startType.toUpperCase();
					String ct2 = endType.toUpperCase();
					complexAdder(n, ct2, "stop", notations, technical);
					complexAdder(n, ct1, "start", notations, technical);
				} else if (!endType.equals("")) {
					String ct = endType.toUpperCase();
					complexAdder(n, ct, "stop", notations, technical);
				} else {
					String ct = startType.toUpperCase();
					complexAdder(n, ct, "stop", notations, technical);
				}
			}

			// <harmonic>
			if (n.isHarmonic) {
				e = doc.createElement("harmonic");

				attr = doc.createAttribute("placement");
				attr.setValue("above");
				e.setAttributeNode(attr);

				attr = doc.createAttribute("print-object");
				attr.setValue("yes");
				e.setAttributeNode(attr);

				technical.appendChild(e);
			}

			// --<string>
			e = doc.createElement("string");
			e.appendChild(doc.createTextNode(String.valueOf(n.string)));
			technical.appendChild(e);

			// --<fret>
			e = doc.createElement("fret");
			e.appendChild(doc.createTextNode(String.valueOf(n.fret)));
			technical.appendChild(e);
		}

	}

	private static String getComplexTypeString(String complexType) {
		String complexTypeString;

		if (complexType.equals("H")) {
			complexTypeString = "hammer-on";
		} else if (complexType.equals("P")) {
			complexTypeString = "pull-off";
		} else if (complexType.equals("S")) {
			complexTypeString = "slide";
		} else {
			complexTypeString = "complexTypeStringNotFound";
		}
		return complexTypeString;
	}

	private static void complexAdder(Note n, String complexType, String startOrStop, Element notations,
			Element technical) {

		int complexTypeNumber;
		String complexTypeString = getComplexTypeString(complexType);
		Element e;
		Attr attr;

		if (complexType.equals("H")) {
			complexTypeNumber = StringParserUtility.hammerOnCount;
		} else if (complexType.equals("P")) {
			complexTypeNumber = StringParserUtility.pullOffCount;
		} else if (complexType.equals("S")) {
			complexTypeNumber = StringParserUtility.slideCount;
		} else {
			complexTypeNumber = 1;
		}

		if (complexTypeString == "slide") {
			e = doc.createElement(complexTypeString);

			attr = doc.createAttribute("line-type");
			attr.setValue("solid");
			e.setAttributeNode(attr);

			attr = doc.createAttribute("number");
			attr.setValue(String.valueOf(complexTypeNumber));
			e.setAttributeNode(attr);

			notations.appendChild(e);
		} else {
			e = doc.createElement(complexTypeString);
			e.appendChild(doc.createTextNode(complexType));

			attr = doc.createAttribute("number");
			attr.setValue(String.valueOf(complexTypeNumber));
			e.setAttributeNode(attr);

			attr = doc.createAttribute("type");
			attr.setValue(startOrStop);
			e.setAttributeNode(attr);

			technical.appendChild(e);

			// <slur>
			e = doc.createElement("slur");
			attr = doc.createAttribute("number");
			attr.setValue(String.valueOf(complexTypeNumber));
			e.setAttributeNode(attr);

			attr = doc.createAttribute("placement");
			attr.setValue("above");
			e.setAttributeNode(attr);

			attr = doc.createAttribute("type");
			attr.setValue(startOrStop);
			e.setAttributeNode(attr);

			notations.appendChild(e);
		}

		if (startOrStop.equals("stop")) {
			if (complexType.equals("H")) {
				StringParserUtility.hammerOnCount = (StringParserUtility.hammerOnCount + 1) % 6;
			} else if (complexType.equals("P")) {
				StringParserUtility.pullOffCount = (StringParserUtility.pullOffCount + 1) % 6;
			} else if (complexType.equals("S")) {
				StringParserUtility.slideCount = (StringParserUtility.slideCount + 1) % 6;
			}
		}

	}

	// addBarline(measure element, "left" / "right", # of repeats from measure)
	private static void addBarline(Element measureElem, String LeftRight, int numRepeats) {

		String direction_value = "";
		if (LeftRight.equals("left")) {
			direction_value = "forward";
		} else {
			direction_value = "backward";
		}

		// <barline>
		Element barline = doc.createElement("barline");
		Attr attr = doc.createAttribute("location");
		attr.setValue(LeftRight);
		barline.setAttributeNode(attr);
		measureElem.appendChild(barline);

		// <bar-style>
		Element e = doc.createElement("bar-style");
		e.appendChild(doc.createTextNode("heavy-light"));
		barline.appendChild(e);

		e = doc.createElement("repeat");
		attr = doc.createAttribute("direction");
		attr.setValue(direction_value);
		e.setAttributeNode(attr);
		barline.appendChild(e);

		if (direction_value.equals("forward")) {
			// <direction> only for the first barline
			// displays the text that says how many times to repeat
			Element directionElem = doc.createElement("direction");
			attr = doc.createAttribute("placement");
			attr.setValue("above");
			directionElem.setAttributeNode(attr);
			measureElem.appendChild(directionElem);

			// <direction-type>
			Element directionType = doc.createElement("direction-type");
			directionElem.appendChild(directionType);

			// <words>
			e = doc.createElement("words");
			attr = doc.createAttribute("relative-x"); // relative-x
			attr.setValue(String.valueOf(256.17));
			e.setAttributeNode(attr);
			directionType.appendChild(e);

			attr = doc.createAttribute("relative-y"); // relative-y
			attr.setValue(String.valueOf(16.01));
			e.setAttributeNode(attr);
			// repeat x times
			e.appendChild(doc.createTextNode("Repeat " + String.valueOf(numRepeats) + " times"));
			directionType.appendChild(e);
		}

	}

	// Adds the guitar-specific staff details instructions to the XML file built
	// Input is the root element of the <staff-details> tag
	private static void addStaffDetails(Element staffDetailsElement, String instrument) {
		// ts => tuning-step
		// to => tuning octave

		// 2d array for each <tuning-step> and its respective <tuning-octave>
		// String[][] tuning = { { "E", "2" }, { "A", "2" }, { "D", "3" }, { "G", "3" },
		// { "B", "3" }, { "E", "4" } };

		Note[][] tuning;

		if (instrument.equals("GUITAR")) {
			NoteUtility nu = TuningController.NU;
			tuning = nu.guitarNote;
		} else {
			BassNoteUtility nu = TuningController.BNU;
			tuning = nu.BassNote;

		}

		int staffLines = tuning.length;

		// -<staff-lines>
		Element e = doc.createElement("staff-lines");
		e.appendChild(doc.createTextNode(String.valueOf(staffLines)));
		staffDetailsElement.appendChild(e);

		for (int i = 0; i < staffLines; i++) {
			// -<staff-tuning>
			Element staffTuning = doc.createElement("staff-tuning");
			staffDetailsElement.appendChild(staffTuning);

			// add "line" attribute to <staff-tuning>
			Attr attr = doc.createAttribute("line");
			attr.setValue(String.valueOf(i + 1));
			staffTuning.setAttributeNode(attr);

			// --<tuning-step>
			e = doc.createElement("tuning-step");
			e.appendChild(doc.createTextNode(String.valueOf(tuning[i][0].getStep())));
			staffTuning.appendChild(e);

			// --<tuning-octave>
			e = doc.createElement("tuning-octave");
			e.appendChild(doc.createTextNode(String.valueOf(tuning[i][0].getOctave())));
			staffTuning.appendChild(e);
		}
	}

}