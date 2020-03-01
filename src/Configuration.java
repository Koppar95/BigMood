import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Configuration{
        public static int height;
        public static int width;
        public static String color;

    public static void parseConfig() throws SAXException,
            IOException, ParserConfigurationException {
        File xmlFile = new File("xml/config.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
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
}
