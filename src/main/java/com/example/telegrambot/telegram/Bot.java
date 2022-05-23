package com.example.telegrambot.telegram;

import com.example.telegrambot.reminder.ReminderData;
import com.example.telegrambot.service.ReminderService;
import com.example.telegrambot.ResponseMessageBuilder;
import com.example.telegrambot.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

@Component
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {

    private final TelegramService telegramService;
    private final ReminderService reminderService;
    private final ResponseMessageBuilder responseMessageBuilder;


    @Value("${telegram.bot-name}")
    private String botName;
    @Value("${telegram.bot-token}")
    private String botToken;


    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (isReminder(update) && responseMessageBuilder.getBusDepartureTime() != null) {
            startReminder(update);
        } else {
            sendMessage(telegramService.startProcess(update));
        }
    }

    public void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void startReminder(Update update) {
        ReminderData reminderData = reminderService.getReminderData(update, responseMessageBuilder.getBusDepartureTime());
        new Timer().schedule(new Reminder(reminderData.getOutMessage()), reminderData.getTimeReminder());
    }

    private boolean isReminder(Update update) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText().toLowerCase().startsWith("за ");
    }

    private class Reminder extends TimerTask {
        private final SendMessage sendMessage;

        public Reminder(SendMessage sendMessage) {
            this.sendMessage = sendMessage;
        }

        @Override
        public void run() {
            sendMessage(sendMessage);
        }
    }
}
