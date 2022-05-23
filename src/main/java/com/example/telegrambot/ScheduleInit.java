package com.example.telegrambot;

import com.example.telegrambot.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class ScheduleInit {
    private final ScheduleService scheduleService;

    @PostConstruct
    private void init(){
        scheduleService.setSchedule494FromHome();
        scheduleService.setSchedule315FromBel();
    }
}
