package org.example.handle.reply.keyboard;

import org.example.bots.SimpleBot;
import org.example.entity.ReplyKeyboardButton;
import org.example.utils.ViewCalendar;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
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
    
    public static void processCalendar(String chatId, SimpleBot bot) throws TelegramApiException {
        ViewCalendar.showCalendar(chatId, bot);
    }
    
    private static String getTime() {
        LocalDateTime time = LocalDateTime.now();

        return "```\n" + "Время на сервере: \n" + time.format(DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(new Locale.Builder().setLanguage("ru").build())) +"\n```";
    }
}
