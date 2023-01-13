package org.example.handle.reply.keyboard;

import org.example.bots.SimpleBot;
import org.example.handle.currency.CurrencyParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class BotCurrency {
    public static void processCurrency(String chatId, SimpleBot bot) throws TelegramApiException {
        String answer = CurrencyParser.parseCurrency();
        SendMessage message = SendMessage.builder().chatId(chatId).text(answer).build();
        bot.execute(message);
    }
}
