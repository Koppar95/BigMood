import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
 * This class can read and write to the configuration file of this application
 * @author Samuel Leckborn
 * @version 1.1
 */
public class Configuration{
    /**
     * Int representing window height.
     */
    public static int height;
    /**
     * Int representing window width.
     */
    public static int width;
    /**
     * String representing application colour scheme.
     */
    public static String color;

    /**
     * The document where configuration setting are stored.
     */
    public static Document doc;

    /**
     * Parses the configuration file and stores the values in static varables.
     * Sets default values if the data in the file is in wrong format.
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @since 1.1
     */
    public static boolean parseConfig() throws SAXException, IOException, ParserConfigurationException {


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
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException");
            height = 800;
            width = 900;
        }

        color = node3.getTextContent().toLowerCase();
        if (!(color.equals("green") || color.equals("gold"))) {
            color = "green";
        }
        return true;
    }

    /**
     * Updates the document.
     *
     * @param what        What element in the configuration file to edit.
     * @param updateValue The new value.
     * @throws TransformerException
     * @since 1.1
     */
    public static void updateElementValue(String what, String updateValue) throws TransformerException {
        Element root = doc.getDocumentElement();
        Node color = root.getElementsByTagName(what).item(0).getFirstChild();
        color.setNodeValue(updateValue);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("xml/config.xml"));
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        transformer.transform(source, result);
        System.out.println("XML file updated successfully");
    }

}
