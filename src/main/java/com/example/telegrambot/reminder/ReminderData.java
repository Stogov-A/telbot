package com.example.telegrambot.reminder;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Date;

@Data
@AllArgsConstructor
public class ReminderData {

    private SendMessage outMessage;

    private Date timeReminder;

}