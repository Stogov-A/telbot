package com.example.telegrambot.service;

import com.example.telegrambot.parser.Parser;
import com.example.telegrambot.Utils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Data
public class ScheduleService {

    private static final int MINUTES_FROM_BEL_TO_SESTR_315 = 21;
    private static int MINUTES_FROM_HOME_TO_BEL = 15;
    private static final int MINUTES_ROAD_FROM_494_TO_315 = 5;
    private static final int MINUTES_FROM_HOME_TO_315 = MINUTES_FROM_HOME_TO_BEL + MINUTES_ROAD_FROM_494_TO_315;
    private static final String ROAD_494_FROM_HOME = "ROAD_494_FROM_HOUSE";
    private static final String ROAD_315_FROM_BEL = "ROAD_315_FROM_BEL";

    @Value("${loadSchedule}")
    public boolean loadSchedule;

    private final Parser parser;

    private TreeSet<LocalDateTime> schedule494FromHome;
    private TreeSet<LocalDateTime> schedule315FromBel;
    private String error;


    public LocalDateTime getTimeBus315FromBel(LocalDateTime appointmentLocalDateTime) {
        return getTimeBus(appointmentLocalDateTime, schedule315FromBel, MINUTES_FROM_BEL_TO_SESTR_315);
    }

    public LocalDateTime getTimeBus494FromHouse(LocalDateTime appointmentLocalDateTime) {
        return getTimeBus(appointmentLocalDateTime, schedule494FromHome, MINUTES_FROM_HOME_TO_BEL);
    }

    public LocalDateTime getTimeToSestr(LocalDateTime timeBus315FromBel){
        return timeBus315FromBel.plusMinutes(MINUTES_FROM_BEL_TO_SESTR_315);
    }

    public LocalDateTime getTimeFromHomeToBel(LocalDateTime timeBus494FromHome){
        return timeBus494FromHome.plusMinutes(MINUTES_FROM_HOME_TO_BEL);
    }

    private LocalDateTime getTimeBus(LocalDateTime appointmentLocalDateTime, TreeSet<LocalDateTime> schedule, int minutes) {
        if (!CollectionUtils.isEmpty(schedule)) {
            return schedule.floor(appointmentLocalDateTime.minusMinutes(minutes));
        }
        return null;
    }

    private TreeSet<LocalDateTime> getListScheduleFromString(String schedule) {
        return Arrays.stream(schedule.split(" "))
                .map(Utils::getLocalDateTimeFromString)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Scheduled(cron = "0 59 23 * * ?")
    public void setSchedule494FromHome(){
        String schedule = loadSchedule ? parser.getSchedule494FromHouse() : createSchedule();
        this.schedule494FromHome = getListScheduleFromString(schedule);
    }

    @Scheduled(cron = "0 59 23 * * ?")
    public void setSchedule315FromBel() {
        String schedule = loadSchedule ? parser.getSchedule494FromHouse() : createSchedule();
        this.schedule315FromBel = getListScheduleFromString(schedule);
    }

    private String createSchedule() {
        return "06:30 07:00 07:38 08:08 09:06 09:36 10:36 11:45 12:15 12:45 13:20 13:55 15:51 16:21 16:56 17:44 18:39 19:18 20:22 20:52 21:28 21:58 22:33 23:26";
    }
}
