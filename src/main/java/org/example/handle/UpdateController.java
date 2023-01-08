package org.example.handle;

import org.example.bots.SimpleBot;
import org.example.entity.ReplyKeyboardButton;
import org.example.handle.reply.BotStartReply;
import org.example.utils.ViewCalendar;
import org.example.utils.MessageUtils;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;

public class UpdateController {
    private SimpleBot bot;
    private String chatId;

    public UpdateController(SimpleBot bot) {
        this.bot = bot;
    }

    public void handleUpdate(Update update) throws TelegramApiException {
        if (update.hasMessage() && update.getMessage().hasText()) {
            processTextMessage(update);
        } else if (update.hasCallbackQuery()) {
            processCallbackQuery(update.getCallbackQuery());
        } else {
            processUnsupportedMessageType(update);
        }
    }

    private void processCallbackQuery(CallbackQuery callbackQuery) throws TelegramApiException {
        AnswerCallbackQuery answerCallback = new AnswerCallbackQuery(callbackQuery.getId());
        chatId = callbackQuery.getMessage().getChatId().toString();
        bot.execute(answerCallback);
        
        EditMessageText mes = new EditMessageText();
        mes.setChatId(callbackQuery.getMessage().getChatId());
        mes.setMessageId(callbackQuery.getMessage().getMessageId());
        mes.setParseMode(ParseMode.MARKDOWN);
        
        String callbackData = callbackQuery.getData();
        if (callbackData.contains("Calendar")) {
            ViewCalendar myCalendar = new ViewCalendar(callbackData);
            mes.setText(myCalendar.getCalendar());
            mes.setReplyMarkup(myCalendar.getCalendarKeyboardMarkup());
        }

        bot.execute(mes);
    }

    private void processTextMessage(Update update) throws TelegramApiException {
        Message message = update.getMessage();
        chatId = message.getChatId().toString();
        ReplyKeyboardButton keyboardReply = ReplyKeyboardButton.parse(message.getText());
        if (keyboardReply != null) {
            keyboardReply.sendAnswer(chatId, bot);
        } else if (message.getText().equals("/start")) {
            BotStartReply.startMenu(chatId, bot);
        }
    }

    private void processUnsupportedMessageType(Update update) {
        var sendMessage = MessageUtils.generateSendMessageWithText(chatId,
                "Неподдерживаемый тип сообщения!");
        bot.sendAnswerMessage(sendMessage);
    }
    
    private Message processDice() throws TelegramApiException {
        SendDice dice = new SendDice(chatId);
        return (bot.execute(dice));
    }

    private void workInProgress() throws TelegramApiException {
        SendMessage message = new SendMessage(chatId, "Пока не реализовано");
        bot.execute(message);
    }
}
