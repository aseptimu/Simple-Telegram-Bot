package org.example.handle.reply.keyboard;

import org.checkerframework.checker.units.qual.K;
import org.example.bots.SimpleBot;
import org.example.entity.ReplyKeyboardButton;
import org.example.utils.ViewCalendar;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class BotTime {
    
    public static void processTime(String chatId, SimpleBot bot) throws TelegramApiException {
        bot.execute(SendMessage.builder()
                .chatId(chatId)
                .parseMode(ParseMode.MARKDOWN)
                .text(getTime())
                .replyMarkup(ReplyKeyboardMarkup.builder()
                        .resizeKeyboard(true)
                        .keyboardRow(
                                new KeyboardRow(
                                        Arrays.asList(
                                                KeyboardButton.builder()
                                                        .text(ReplyKeyboardButton.SERVER_TIME.getLabel())
                                                        .build(),
                                                KeyboardButton.builder()
                                                        .text(ReplyKeyboardButton.MY_TIME.getLabel())
                                                        .build()
                                        )
                                ))
                        .keyboardRow(
                                new KeyboardRow(
                                        Collections.singletonList(
                                                KeyboardButton.builder()
                                                        .text(ReplyKeyboardButton.CALENDAR.getLabel())
                                                        .build()
                                        )
                                ))
                        .keyboardRow(
                                new KeyboardRow(
                                        Collections.singletonList(
                                                KeyboardButton.builder()
                                                        .text(ReplyKeyboardButton.HOME.getLabel())
                                                        .build()
                                        )
                                )
                        )
                        .build())
                .build());
    }
    
    public static void processServerTime(String chatId, SimpleBot bot) throws TelegramApiException {
        String messageTime = getTime();

        SendMessage message = new SendMessage(chatId, messageTime);
        message.setParseMode(ParseMode.MARKDOWN);
        message.setReplyMarkup(keyboardTime()); //TODO: delete

        Message m = bot.execute(message);
        m.getWebAppData();
    }
    
    public static void processMyTime(String chatId, SimpleBot bot) throws TelegramApiException {
        String messageTime = getTime();

        SendMessage message = new SendMessage(chatId, messageTime);
        message.setParseMode(ParseMode.MARKDOWN);
        message.setReplyMarkup(keyboardTime()); //TODO: delete

        Message m = bot.execute(message);
        m.getWebAppData();
    }
    
    public static void processCalendar(String chatId, SimpleBot bot) throws TelegramApiException {
        ViewCalendar.showCalendar(chatId, bot);
    }
    
    private static String getTime() {
        LocalDateTime time = LocalDateTime.now();

        return "```\n" + "Время на сервере: " + time.format(DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(new Locale.Builder().setLanguage("ru").build())) +"\n```";
    }
    
    private static ReplyKeyboard keyboardTime() {
        KeyboardRow keyBackRow = new KeyboardRow();
        keyBackRow.add("Назад");
        
        KeyboardRow myTimeRow = new KeyboardRow();
        KeyboardButton myTime = new KeyboardButton();
        myTime.setWebApp(WebAppInfo.builder().url("https://expented.github.io/tgdtp/").build());
        myTime.setText("Моё время");
        myTimeRow.add(myTime);
        
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(List.of(myTimeRow, keyBackRow));
        keyboardMarkup.setResizeKeyboard(true);
        
        return keyboardMarkup;
    }
}
