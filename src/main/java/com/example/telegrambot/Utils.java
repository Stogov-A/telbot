package com.example.telegrambot;

import java.time.LocalDateTime;

public class Utils {
    public static String getTimeFromText(String time) {
        int charIndex = time.indexOf(':');
        if (charIndex < 0) {
            return "16:00";
        }
        return time.substring(charIndex - 2, charIndex + 3);
    }

    public static String getMinutesFromText(String text){
        return text.substring(3);
    }

    public static LocalDateTime getLocalDateTimeFromString(String time){
        return LocalDateTime.now().withHour(getHour(time)).withMinute(getMinutes(time));
    }

    private static int getHour(String time) {
        return Integer.parseInt(time.substring(0, 2));
    }

    private static int getMinutes(String time) {
        return Integer.parseInt(time.substring(3, 5));
    }

}
