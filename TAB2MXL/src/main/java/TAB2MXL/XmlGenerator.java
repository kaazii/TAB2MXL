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

			// Check for drum instruments
			// get a sample note and check if it's a DrumNote
			// isDrums = isDrums(measureList);
			Measure m = measureList.get(0);
			Note n = m.getNoteList().get(0);

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

			// fill in attributes ALL measures
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

			// -<clef> and -<staff-details> ONLY FOR THE FIRST MEASURE
			if (m.measureNumber == 1) {
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
					addGuitarStaffDetails(staffDetails);
					measureAttribute.appendChild(staffDetails);
				}
			}
			// Barline logic for repeats - LEFT BARLINE
			if (m.repeatStart && m.repeats > 0) {
				addBarline(measureElem, "left", m.repeats);
			}

			measureNum++;
			// Add notes
			for (Note n : m.noteList) {
				// <note>
				Element note = doc.createElement("note");
				measureElem.appendChild(note);

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
				//stem if it's drums
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
					if (n.beam != null) {
						e = doc.createElement("beam");
						attr = doc.createAttribute("number");
						attr.setValue(String.valueOf(n.beam.number));
						e.setAttributeNode(attr);
						e.appendChild(doc.createTextNode(n.beam.state));
						note.appendChild(e);
					}
				}

				// -<notations>
				if (xutil.includeNotations) {
					Element notations = doc.createElement("notations");
					note.appendChild(notations);

					// -<technical>
					Element technical = doc.createElement("technical");
					notations.appendChild(technical);

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

			// right Barline
			if (m.repeatEnd && m.repeats > 0) {
				addBarline(measureElem, "right", m.repeats);
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
	private static void addGuitarStaffDetails(Element staffDetailsElement) {
		// ts => tuning-step
		// to => tuning octave
		int guitarStaffLines = 6;

		// 2d array for each <tuning-step> and its respective <tuning-octave>
		String[][] tuning = { { "E", "2" }, { "A", "2" }, { "D", "3" }, { "G", "3" }, { "B", "3" }, { "E", "4" } };

		// -<staff-lines>
		Element e = doc.createElement("staff-lines");
		e.appendChild(doc.createTextNode(String.valueOf(guitarStaffLines)));
		staffDetailsElement.appendChild(e);

		for (int i = 0; i < guitarStaffLines; i++) {
			// -<staff-tuning>
			Element staffTuning = doc.createElement("staff-tuning");
			staffDetailsElement.appendChild(staffTuning);

			// add "line" attribute to <staff-tuning>
			Attr attr = doc.createAttribute("line");
			attr.setValue(String.valueOf(i + 1));
			staffTuning.setAttributeNode(attr);

			// --<tuning-step>
			e = doc.createElement("tuning-step");
			e.appendChild(doc.createTextNode(String.valueOf(tuning[i][0])));
			staffTuning.appendChild(e);

			// --<tuning-octave>
			e = doc.createElement("tuning-octave");
			e.appendChild(doc.createTextNode(String.valueOf(tuning[i][1])));
			staffTuning.appendChild(e);
		}

	}

	private static void addBassStaffDetails(Element staffDetailsElement) {
		// ts => tuning-step
		// to => tuning octave
		int bassStaffLines = 4;

		// 2d array for each <tuning-step> and its respective <tuning-octave>
		String[][] tuning = { { "E", "1" }, { "A", "1" }, { "B", "2" }, { "G", "2" } };

		// -<staff-lines>
		Element e = doc.createElement("staff-lines");
		e.appendChild(doc.createTextNode(String.valueOf(bassStaffLines)));
		staffDetailsElement.appendChild(e);

		for (int i = 0; i < bassStaffLines; i++) {
			// -<staff-tuning>
			Element staffTuning = doc.createElement("staff-tuning");
			staffDetailsElement.appendChild(staffTuning);

			// add "line" attribute to <staff-tuning>
			Attr attr = doc.createAttribute("line");
			attr.setValue(String.valueOf(i + 1));
			staffTuning.setAttributeNode(attr);

			// --<tuning-step>
			e = doc.createElement("tuning-step");
			e.appendChild(doc.createTextNode(String.valueOf(tuning[i][0])));
			staffTuning.appendChild(e);

			// --<tuning-octave>
			e = doc.createElement("tuning-octave");
			e.appendChild(doc.createTextNode(String.valueOf(tuning[i][1])));
			staffTuning.appendChild(e);
		}

	}
}