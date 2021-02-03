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

public class MxlGenerator {

   public static void main(String argv[]) {

      try {
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.newDocument();
         
         // root element
         Element rootElement = doc.createElement("score-partwise");
         doc.appendChild(rootElement);

         // part-list element
         Element partList = doc.createElement("part-list");
         rootElement.appendChild(partList);
         
         // score-part element
         Element scorePart = doc.createElement("score-part");
         rootElement.appendChild(scorePart);

         // setting attribute to element
         Attr attr = doc.createAttribute("id");
         attr.setValue("P1");
         partList.setAttributeNode(attr);

//         // carname element
//         Element carname = doc.createElement("carname");
//         Attr attrType = doc.createAttribute("type");
//         attrType.setValue("formula one");
//         carname.setAttributeNode(attrType);
//         carname.appendChild(doc.createTextNode("Ferrari 101"));
//         supercar.appendChild(carname);
//
//         Element carname1 = doc.createElement("carname");
//         Attr attrType1 = doc.createAttribute("type");
//         attrType1.setValue("sports");
//         carname1.setAttributeNode(attrType1);
//         carname1.appendChild(doc.createTextNode("Ferrari 202"));
//         supercar.appendChild(carname1);

         // write the content into xml file
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer = transformerFactory.newTransformer();
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
         transformer.setOutputProperty(OutputKeys.METHOD, "xml");
         DOMImplementation domImpl = dBuilder.getDOMImplementation();
         DocumentType doctype = domImpl.createDocumentType("doctype",
             "-//Recordare//DTD MusicXML 3.1 Partwise//EN",
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
}