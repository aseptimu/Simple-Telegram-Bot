package org.example.handle.currency;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {
    
    private static Document document;

    public static Document execute(String resource) throws IOException, ParserConfigurationException, SAXException {
        document = loadXML(resource);
        return document;
    }

    public static String getCharCode(Document document, String charCodeValue) {
        NodeList valutes = document.getElementsByTagName("Valute");
        for (int i = 0; i < valutes.getLength(); i++) {
            Element valute = (Element) valutes.item(i);
            String charCode = valute.getElementsByTagName("CharCode").item(0).getTextContent();
            if(charCode.equals(charCodeValue)) {
                return "" + charCode + ": " 
                        + valute.getElementsByTagName("Value").item(0).getTextContent()
                        + " RUB";
            }
        }
        return null;
    }

    public static Document loadXML(String urlString) throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        URL url = new URL(urlString);
        InputStream input = url.openStream();
        return builder.parse(input);
    }
}
