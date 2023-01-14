package org.example.handle.currency;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CurrencyParser {

//    private final static String CRYPTO_COURSES = "https://api.coindesk.com/v1/bpi/currentprice.json";
    private final static String CBR_COURSES = "http://www.cbr.ru/scripts/XML_daily.asp";
    
    private static List<Currency> initCurrencies() {
        List<Currency> currencies = new ArrayList<>();
        Collections.addAll(currencies, Currency.USD, Currency.EUR, Currency.GBP, Currency.BYR);
        return currencies;
    }
    
    public static String processCurrency() {
        List<Currency> currencies = initCurrencies();
        return parseCurrency(currencies);
    }

    private static String parseCurrency(List<Currency> currencies) {
        String answer = "```\n" + "Не удалось получить курс валют" + "```"; //TODO: реализовать крипто и executorService который будет раз в некоторое время парсить курсы.
        try {
            Document document = XMLParser.execute(CBR_COURSES);
            StringBuilder currencyCourses = new StringBuilder("```\n");
            for (Currency currency : currencies) {
                String s = XMLParser.getCharCode(document, currency);
                if (s != null) {
                    currencyCourses.append(s);
                }
            }
            currencyCourses.append("\nОфициальный курс ЦБ РФ на ").append(XMLParser.getDate()).append("\n```");
            return currencyCourses.toString();
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return answer;
    }
}
