package org.example.entity;

import org.example.bots.SimpleBot;
import org.example.handle.reply.BotStartReply;
import org.example.handle.reply.keyboard.BotCurrency;
import org.example.handle.reply.keyboard.BotTime;
import org.example.handle.reply.keyboard.BotWeather;
import org.example.handle.reply.keyboard.Games;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public enum ReplyKeyboardButton implements ReplyKeyboardButtonAction {
    TIME("Время \u23F1") {
        @Override
        public void sendAnswer(String chatId, SimpleBot bot) throws TelegramApiException {
            BotTime.processTime(chatId, bot);
        }
    },
    GAME("Испытать удачу \uD83C\uDF40") {
        @Override
        public void sendAnswer(String chatId, SimpleBot bot) throws TelegramApiException {
            Games.selectGame(chatId, bot);
        }
    },
    CURRENCY("Курс валют \uD83D\uDCB5") {
        @Override
        public void sendAnswer(String chatId, SimpleBot bot) throws TelegramApiException {
            BotCurrency.processCurrency(chatId, bot);
        }
    },
    WEATHER("Погода \uD83C\uDF24") {
        @Override
        public void sendAnswer(String chatId, SimpleBot bot) throws TelegramApiException {
            BotWeather.processWeather(chatId, bot);
        }
    },

    CALENDAR("Календарь \uD83D\uDDD3") {
        @Override
        public void sendAnswer(String chatId, SimpleBot bot) throws TelegramApiException {
            BotTime.processCalendar(chatId, bot);
        }
    },
    SERVER_TIME("Время на сервере") {
        @Override
        public void sendAnswer(String chatId, SimpleBot bot) throws TelegramApiException {
            BotTime.processServerTime(chatId, bot);
        }
    },
    MY_TIME("Моё время") {
        @Override
        public void sendAnswer(String chatId, SimpleBot bot) throws TelegramApiException {
            BotTime.processMyTime(chatId, bot);
        }
    },

    
    DICE("Кубик \uD83C\uDFB2") {
        @Override
        public void sendAnswer(String chatId, SimpleBot bot) throws TelegramApiException {
            Games.playGame(chatId, bot, this);
        }
    },
    DARTS("Дартс \uD83C\uDFAF") {
        @Override
        public void sendAnswer(String chatId, SimpleBot bot) throws TelegramApiException {
            Games.playGame(chatId, bot, this);
        }
    },
    BASKETBALL("Баскетбол \uD83C\uDFC0") {
        @Override
        public void sendAnswer(String chatId, SimpleBot bot) throws TelegramApiException {
            Games.playGame(chatId, bot, this);
        }
    },
    FOOTBALL("Футбол \u26BD") {
        @Override
        public void sendAnswer(String chatId, SimpleBot bot) throws TelegramApiException {
            Games.playGame(chatId, bot, this);
        }
    },
    BOWLING("Боулинг \uD83C\uDFB3") {
        @Override
        public void sendAnswer(String chatId, SimpleBot bot) throws TelegramApiException {
            Games.playGame(chatId, bot, this);
        }
    },
    SLOT_MACHINE("Автомат \uD83C\uDFB0") {
        @Override
        public void sendAnswer(String chatId, SimpleBot bot) throws TelegramApiException {
            Games.playGame(chatId, bot, this);
        }
    },
    
    HOME("Назад") {
        @Override
        public void sendAnswer(String chatId, SimpleBot bot) throws TelegramApiException {
            BotStartReply.startMenu(chatId, bot);
        }
    };
    
    private final String label;

    ReplyKeyboardButton(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    
    public static ReplyKeyboardButton parse(String name) {
        for (ReplyKeyboardButton k : values()) {
            if (k.label.equals(name) || k.toString().equals(name)) {
                return k;
            }
        }
        return null;
    }
}

