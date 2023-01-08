package org.example.handle.reply;

import org.example.bots.SimpleBot;
import org.example.entity.ReplyKeyboardButton;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;

public class BotStartReply {
    public static void startMenu(String chatId, SimpleBot bot) throws TelegramApiException {
        KeyboardRow key1 = new KeyboardRow();
        key1.add(ReplyKeyboardButton.TIME.getLabel());
        key1.add(ReplyKeyboardButton.GAME.getLabel());
        KeyboardRow key2 = new KeyboardRow();
        key2.add(ReplyKeyboardButton.CURRENCY.getLabel());
        key2.add(ReplyKeyboardButton.WEATHER.getLabel());
        List<KeyboardRow> keys = Arrays.asList(key1, key2);
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup(keys);
        keyboard.setResizeKeyboard(true);
        bot.execute(SendMessage.builder().chatId(chatId).text("Greetings! \uD83D\uDE42").replyMarkup(keyboard).build());
    }
}
