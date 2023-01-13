package org.example.handle.currency;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class CurrencyParser {

    private final static String CRYPTO_COURSES = "https://api.coindesk.com/v1/bpi/currentprice.json";
    private final static String CBR_COURSES = "http://www.cbr.ru/scripts/XML_daily.asp";
    
    public static String parseCurrency() { //TODO: привести к нормальному виду. Возможно, добавить List с валютами и по нему пробегаться при парсинге cbr.
        String answer = "```\n" + "Не удалось получить курс валют" + "```"; //TODO: реализовать крипто и executorService который будет раз в некоторое время парсить курсы.
        try {
            Document document = XMLParser.execute(CBR_COURSES);
            String Courses = XMLParser.getCharCode(document, "USD") + "\n";
            Courses += XMLParser.getCharCode(document, "EUR") + "\n";
            Courses += XMLParser.getCharCode(document, "GBP") + "\n";
            Courses += "\n Официальный курс ЦБ РФ";
            return Courses;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return answer;
    }
}
