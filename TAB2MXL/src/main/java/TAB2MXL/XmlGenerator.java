package TAB2MXL;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
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
import java.io.File;
import java.util.ArrayList;

public class XmlGenerator {

	public static final String PART_NAME = "Classical Guitar";
	private static Document doc;
	
	private static String divisions = String.valueOf(Measure.divisions);
	private static String fifths = String.valueOf(0);
	
	private static String barlineLocation = "right";
	private static String barStyle = "light-heavy";
	

	public static void Generate(ArrayList<Measure> measureList) {

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
			partName.appendChild(doc.createTextNode(PART_NAME));

			// Create part 1
			Element part1 = doc.createElement("part");
			attr = doc.createAttribute("id");
			attr.setValue("P1");
			part1.setAttributeNode(attr);
			rootElement.appendChild(part1);

			addMeasures(part1, measureList);

			// write the content into xml file
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
			StreamResult result = new StreamResult(new File("D:\\eclipse-workspace\\EECS2311\\test.xml"));
			transformer.transform(source, result);

			// Output to console for testing
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(source, consoleResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addMeasures(Element partElement, ArrayList<Measure> measureList) {
		// Initial, unchanging values
		int measureNum = 1;
		
		// Iterate through Measures list
		for (Measure m : measureList) {
			// Create measure tag and add number attribute as well as it's value
			Element measureElem = doc.createElement("measure");
			Attr attr = doc.createAttribute("number");
			attr.setValue(String.valueOf(measureNum));
			measureElem.setAttributeNode(attr);
			partElement.appendChild(measureElem);
			
			// <attributes>
			Element measureAttribute = doc.createElement("attributes");
			measureElem.appendChild(measureAttribute);
			
			// -<divisions>
			Element e = doc.createElement("divisions");
			e.appendChild(doc.createTextNode(XmlGenerator.divisions));
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
			e.appendChild(doc.createTextNode(Measure.clefSign));
			clef.appendChild(e);
			
			// --<line>
			e = doc.createElement("line");
			e.appendChild(doc.createTextNode(String.valueOf(m.clefLine)));
			clef.appendChild(e);
			
			measureNum++;
			
			// -<staff-details>
			Element staffDetails = doc.createElement("staff-details");
			addGuitarStaffDetails(staffDetails);
			measureAttribute.appendChild(staffDetails);
			
			// Add notes
			for (Note n : m.notelist) {
				//<note>
				Element note = doc.createElement("note");
				measureElem.appendChild(note);
				
				//-<pitch>
				Element pitch = doc.createElement("pitch");
				note.appendChild(pitch);
				
				// --<step>
				e = doc.createElement("step");
				e.appendChild(doc.createTextNode(n.step));
				pitch.appendChild(e);
				
				// --<octave>
				e = doc.createElement("octave");
				e.appendChild(doc.createTextNode(String.valueOf(n.octave)));
				pitch.appendChild(e);
				
				// -<duration>
				e = doc.createElement("duration");
				e.appendChild(doc.createTextNode(String.valueOf(n.duration)));
				note.appendChild(e);
				
				// -<voice>
				e = doc.createElement("voice");
				e.appendChild(doc.createTextNode(String.valueOf(n.voice)));
				note.appendChild(e);
				
				// -<type>
				e = doc.createElement("type");
				e.appendChild(doc.createTextNode(n.type));
				note.appendChild(e);
				
				// -<notations>
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
			
			// Add barline info
			// <barline location="right">
			Element barline = doc.createElement("barline");
			measureElem.appendChild(barline);
			
			attr = doc.createAttribute("location");
			attr.setValue(barlineLocation);
			barline.setAttributeNode(attr);
			
			//-<bar-style>
			e = doc.createElement("bar-style");
			e.appendChild(doc.createTextNode(barStyle));
			barline.appendChild(e);
		}
	}
	
	// Adds the guitar-specific staff details instructions to the XML file built
	// Input is the root element of the <staff-details> tag
	private static void addGuitarStaffDetails(Element staffDetailsElement) {
		// ts => tuning-step
		// to => tuning octave
		int guitarStaffLines = 6;
		
		// 	2d array for each <tuning-step> and its respective <tuning-octave>
		String[][] tuning = {
				{"E", "2"},
				{"A", "2"},
				{"D", "3"},
				{"G", "3"},
				{"B", "3"},
				{"E", "4"}	
		};
		
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
			
			//--<tuning-step>
			e = doc.createElement("tuning-step");
			e.appendChild(doc.createTextNode(String.valueOf(tuning[i][0])));
			staffTuning.appendChild(e);
			
			//--<tuning-octave>
			e = doc.createElement("tuning-octave");
			e.appendChild(doc.createTextNode(String.valueOf(tuning[i][1])));
			staffTuning.appendChild(e);
		}
		
	}
}