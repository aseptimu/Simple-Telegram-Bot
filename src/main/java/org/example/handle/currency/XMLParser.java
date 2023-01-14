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
    private static String date;

    public static Document execute(String resource) throws IOException, ParserConfigurationException, SAXException {
        document = loadXML(resource);
        initDate();
        return document;
    }

    public static String getDate() {
        return date;
    }

    public static String getCharCode(Document document, Currency charCodeValue) {
        NodeList currencies = document.getElementsByTagName("Valute");
        for (int i = 0; i < currencies.getLength(); i++) {
            Element currency = (Element) currencies.item(i);
            String charCode = currency.getElementsByTagName("CharCode").item(0).getTextContent();
            if(charCode.equals(charCodeValue.toString())) {
                return charCodeWrapper(charCodeValue, 
                        currency.getElementsByTagName("Value").item(0).getTextContent());
            }
        }
        return null;
    }
    
    private static String charCodeWrapper(Currency currency, String value) {

        return currency + currency.getSign() + ": " + value.substring(0, value.length() - 2) + " RUB\n";
    }

    private static void initDate() {
        Element valCurs = document.getDocumentElement();
        date = valCurs.getAttribute("Date");
    }
    
    public static Document loadXML(String urlString) throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        URL url = new URL(urlString);
        InputStream input = url.openStream();
        return builder.parse(input);
    }
}
