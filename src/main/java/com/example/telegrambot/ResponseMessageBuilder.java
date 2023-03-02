package com.example.telegrambot;

import com.example.telegrambot.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.example.telegrambot.Utils.getLocalDateTimeFromString;

@Component
@RequiredArgsConstructor
public class ResponseMessageBuilder {
    private static final String ANSWER_FROM_HOME_TO_SESTR = "Автобус от дома отходит в %s \n" +
            "Автобус от белоострова отходит в %s \n" +
            "В сестрорецке на вокзале в %s";
    private static final String ANSWER_FROM_HOME_TO_BEL = "Автобус от дома отходит в %s \n" +
            "В белоострове в %s";
    private static final String ANSWER_ERROR = "Расписание автобуса №%s не загрузилось";
    private static final String ANSWER_REMINDER = "Автобус отправится через %s минут";
    private static final String ANSWER_ERROR_REMINDER = "Для чего напоминание?";
    private static final String BUS_315 = "315";
    private static final String BUS_494 = "494";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final ScheduleService scheduleService;

    public static LocalDateTime busDepartureTime;

    public LocalDateTime getBusDepartureTime() {
        return busDepartureTime;
    }

    public void clearBusDepartureTime() {
        busDepartureTime = null;
    }

    public String getAnsFromHomeToSestr(String appointmentTime) {
        LocalDateTime appointmentLocalDateTime = getLocalDateTimeFromString(appointmentTime);
        LocalDateTime timeBus315FromBel = scheduleService.getTimeBus315FromBel(appointmentLocalDateTime);
        if (Objects.isNull(timeBus315FromBel)) {
            return getAnswerError(BUS_315);
        }
        LocalDateTime timeBus494FromHouse = scheduleService.getTimeBus494FromHouse(timeBus315FromBel);
        if (Objects.isNull(timeBus494FromHouse)) {
            return getAnswerError(BUS_494);
        }
        LocalDateTime timeInSestr = scheduleService.getTimeToSestr(timeBus315FromBel);
        busDepartureTime = timeBus494FromHouse;

        return String.format(ANSWER_FROM_HOME_TO_SESTR,
                timeBus494FromHouse.format(FORMATTER),
                timeBus315FromBel.format(FORMATTER),
                timeInSestr.format(FORMATTER));
    }

    public String getAnsFromHomeToBel(String appointmentTime) {
        LocalDateTime appointmentLocalDateTime = getLocalDateTimeFromString(appointmentTime);
        LocalDateTime timeBus494FromHouse = scheduleService.getTimeBus494FromHouse(appointmentLocalDateTime);
        if (Objects.isNull(timeBus494FromHouse)) {
            return getAnswerError(BUS_494);
        }
        busDepartureTime = timeBus494FromHouse;
        LocalDateTime timeBusInBel = scheduleService.getTimeFromHomeToBel(timeBus494FromHouse);
        return String.format(
                ANSWER_FROM_HOME_TO_BEL,
                timeBus494FromHouse.format(FORMATTER),
                timeBusInBel.format(FORMATTER));
    }

    public String getAnsErrReminder() {
        return ANSWER_ERROR_REMINDER;
    }

    public String getAnsReminder(String busDepartureTime) {
        return String.format(ANSWER_REMINDER, busDepartureTime);
    }

    private String getAnswerError(String numberOfBus) {
        return String.format(ANSWER_ERROR, numberOfBus);
    }

}
