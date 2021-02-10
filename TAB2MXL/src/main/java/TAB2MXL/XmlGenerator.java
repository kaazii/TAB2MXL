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
			//StreamResult result = new StreamResult(new File("D:\\eclipse-workspace\\EECS2311\\test.xml")); -> Amer's version
			StreamResult result = new StreamResult(new File("C:\\Users\\uwais\\git\\EECS2311\\test.xml"));
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
		}
	}
}