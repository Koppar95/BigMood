import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
/**
 * Class for creating a Login Box and checking login authentication
 * Sets a current user variable on successful login to track who is currently logged in.
 * @author Samuel Leckborn
 * @version 1.0
 * @since 2020-03-01
 */
public class Configuration{
        public static int height;
        public static int width;
        public static String color;
        public static Document doc;

    public static void parseConfig() throws SAXException,
            IOException, ParserConfigurationException {
        File xmlFile = new File("xml/config.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        Element root = doc.getDocumentElement();
        Node node1 = root.getElementsByTagName("height").item(0);
        Node node2 = root.getElementsByTagName("width").item(0);
        Node node3 = root.getElementsByTagName("color").item(0);

        try {
            height = Integer.parseInt(node1.getTextContent());
            width = Integer.parseInt(node2.getTextContent());
        }
        catch (NumberFormatException e)
        {
            System.out.println("NumberFormatException");
            height = 600;
            width = 600;
        }

        color=node3.getTextContent().toLowerCase();
        if(!(color.equals("green")||color.equals("gold"))){
            color="green";
        }
    }

    public static void updateElementValue(String updateValue) throws TransformerException {
        Element root = doc.getDocumentElement();

        Node color = root.getElementsByTagName("color").item(0).getFirstChild();
        color.setNodeValue(updateValue);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("xml/config.xml"));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
        System.out.println("XML file updated successfully");
    }
}
