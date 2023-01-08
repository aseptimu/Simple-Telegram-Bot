package org.example.handle.reply.keyboard;

import org.example.bots.SimpleBot;
import org.example.entity.ReplyKeyboardButton;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

public class Games {
    public static void selectGame(String chatId, SimpleBot bot) throws TelegramApiException {
        bot.execute(SendMessage.builder()
                .chatId(chatId)
                .text("Выберите игру")
                .replyMarkup(ReplyKeyboardMarkup.builder()
                        .resizeKeyboard(true)
                        .keyboardRow(
                                new KeyboardRow(
                                        Arrays.asList(
                                                KeyboardButton.builder()
                                                        .text(ReplyKeyboardButton.DICE.getLabel())
                                                        .build(),
                                                KeyboardButton.builder()
                                                        .text(ReplyKeyboardButton.DARTS.getLabel())
                                                        .build(),
                                                KeyboardButton.builder()
                                                        .text(ReplyKeyboardButton.BASKETBALL.getLabel())
                                                        .build())
                                ))
                        .keyboardRow(
                                new KeyboardRow(
                                        Arrays.asList(
                                                KeyboardButton.builder()
                                                        .text(ReplyKeyboardButton.FOOTBALL.getLabel())
                                                        .build(),
                                                KeyboardButton.builder()
                                                        .text(ReplyKeyboardButton.BOWLING.getLabel())
                                                        .build(),
                                                KeyboardButton.builder()
                                                        .text(ReplyKeyboardButton.SLOT_MACHINE.getLabel())
                                                        .build()
                                        )
                                ))
                        .keyboardRow(
                                new KeyboardRow(
                                        Arrays.asList(
                                                KeyboardButton.builder()
                                                        .text(ReplyKeyboardButton.HOME.getLabel())
                                                        .build()
                                        )
                                ))
                        .build())
                .build());
    }
    
    public static void playGame(String chatId, SimpleBot bot, ReplyKeyboardButton gameType) throws TelegramApiException {
        switch (gameType) {
            case DICE -> bot.execute(SendDice.builder().chatId(chatId).emoji("\uD83C\uDFB2").build());
            case DARTS -> bot.execute(SendDice.builder().chatId(chatId).emoji("\uD83C\uDFAF").build());
            case BASKETBALL -> bot.execute(SendDice.builder().chatId(chatId).emoji("\uD83C\uDFC0").build());
            case FOOTBALL -> bot.execute(SendDice.builder().chatId(chatId).emoji("\u26BD").build());
            case BOWLING -> bot.execute(SendDice.builder().chatId(chatId).emoji("\uD83C\uDFB3").build());
            case SLOT_MACHINE -> bot.execute(SendDice.builder().chatId(chatId).emoji("\uD83C\uDFB0").build());
        }
    }
}
