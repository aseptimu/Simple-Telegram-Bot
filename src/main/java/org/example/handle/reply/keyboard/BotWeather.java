package org.example.handle.reply.keyboard;

import org.example.bots.SimpleBot;
import org.example.handle.weather.ParseWeather;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BotWeather {
    public static void processWeather(String chatId, SimpleBot bot) throws TelegramApiException {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .parseMode(ParseMode.MARKDOWN)
                .text(ParseWeather.getWeather("Moscow"))
                .build();
        bot.execute(message);
    }
}
