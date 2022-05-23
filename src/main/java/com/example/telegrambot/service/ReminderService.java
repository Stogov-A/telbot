package com.example.telegrambot.service;

import com.example.telegrambot.ResponseMessageBuilder;
import com.example.telegrambot.reminder.ReminderData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import static com.example.telegrambot.Utils.getMinutesFromText;
import static com.example.telegrambot.service.TelegramService.createSendMessage;

@Component
@RequiredArgsConstructor
public class ReminderService {

    private final ResponseMessageBuilder responseMessageBuilder;

    private Date getReminderStartTime(LocalDateTime scheduleBus, String timeFromReminderToDeparture){
        LocalDateTime reminderStartTime = scheduleBus.minusMinutes(Long.parseLong(timeFromReminderToDeparture));
        return Date.from(reminderStartTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public ReminderData getReminderData(Update update, LocalDateTime busDepartureTime) {
        String minutesToBusDepFromReminder = getMinutesFromText(update.getMessage().getText());
        String ans = responseMessageBuilder.getAnsReminder(minutesToBusDepFromReminder) ;
        SendMessage message = createSendMessage(update, ans);
        Date timeReminder = getReminderStartTime(busDepartureTime, minutesToBusDepFromReminder);
        responseMessageBuilder.clearBusDepartureTime();
        return new ReminderData(message, timeReminder);
    }
}
