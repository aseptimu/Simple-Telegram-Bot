package org.example.entity;

import org.example.bots.SimpleBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface ReplyKeyboardButtonAction {
    void sendAnswer(String chatId, SimpleBot bot) throws TelegramApiException;
}
