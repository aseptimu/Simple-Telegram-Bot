package org.example.bots;

import org.example.handle.UpdateController;
import org.glassfish.grizzly.streams.Input;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SimpleBot extends TelegramLongPollingBot {
    private final String botToken;
    private final String botUsername;

    public SimpleBot() {
        Properties properties = new Properties();
        try  {
            properties.load(ClassLoader.getSystemResourceAsStream("application.properties"));
            botToken = properties.getProperty("botToken");
            botUsername = properties.getProperty("botUsername");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        UpdateController handler = new UpdateController(this);
        try {
            handler.handleUpdate(update);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void sendAnswerMessage(SendMessage message) {
        if (message != null) {
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace(); //TODO: log4j
            }
        }
    }//TODO: remove

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
