package org.example.handle;

import org.example.bots.SimpleBot;
import org.example.utils.MessageUtils;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppData;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
            mes.setReplyMarkup(getCalendarKeyboardMarkup(myCalendar));
        }
        try {
            bot.execute(mes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processTextMessage(Update update) throws TelegramApiException {
        Message message = update.getMessage();
        chatId = message.getChatId().toString();
        switch (message.getText()) {
            case "/start":
            case "Назад" :
                startMenu();
                break;
            case "Время":
                showTime();
                break;
            case "Календарь":
                showCalendar();
                break;
            case "Inline keyboard":
                workInProgress();
//                show();
                break;
            case "Бросить кубик" :
                processDice();
                break;
        }
    }

    private void showCalendar() throws TelegramApiException {
        ViewCalendar myCalendar = new ViewCalendar(null);
        String calendar = myCalendar.getCalendar();
        SendMessage message = new SendMessage(chatId, calendar);
        message.setParseMode(ParseMode.MARKDOWN);
        
        InlineKeyboardMarkup keyboardMarkup = getCalendarKeyboardMarkup(myCalendar);
        message.setReplyMarkup(keyboardMarkup);
        try {
            bot.execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    private InlineKeyboardMarkup getCalendarKeyboardMarkup(ViewCalendar myCalendar) {
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("⬅");
        button1.setCallbackData("previousCalendar " + myCalendar.getCalendarDate());
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("➡");
        button2.setCallbackData("nextCalendar " + myCalendar.getCalendarDate());
        List<InlineKeyboardButton> buttons = Arrays.asList(button1, button2);
        List<List<InlineKeyboardButton>> buttonsList = List.of(buttons);
        return new InlineKeyboardMarkup(buttonsList);
    }

    private void showTime() throws TelegramApiException {
        LocalDateTime time = LocalDateTime.now();
        String messageTime = "```\n" + "Время на сервере: " + time.format(DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(new Locale.Builder().setLanguage("ru").build())) +"\n```";
        SendMessage message = new SendMessage(chatId, messageTime);
        message.setParseMode(ParseMode.MARKDOWN);
        KeyboardRow key2 = new KeyboardRow();
        key2.add("Назад");
        KeyboardRow key1 = new KeyboardRow();
        KeyboardButton but = new KeyboardButton();

        but.setWebApp(WebAppInfo.builder().url("https://expented.github.io/tgdtp/").build());
        
        WebAppData l = new WebAppData();
        but.setText("Моё время");
        key1.add(but);
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(List.of(key1, key2));
        keyboardMarkup.setResizeKeyboard(true);
        message.setReplyMarkup(keyboardMarkup);

        bot.execute(message);
        

        
    }

    private void startMenu() throws TelegramApiException {
        KeyboardRow key1 = new KeyboardRow();
        key1.add("Время");
        key1.add("Календарь");
        KeyboardRow key2 = new KeyboardRow();
        key2.add("Inline keyboard");
        key2.add("Бросить кубик");
        List<KeyboardRow> keys = Arrays.asList(key1, key2);
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup(keys);
        keyboard.setResizeKeyboard(true);
        bot.execute(SendMessage.builder().chatId(chatId).text("Greetings! \uD83D\uDE42").replyMarkup(keyboard).build());
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
