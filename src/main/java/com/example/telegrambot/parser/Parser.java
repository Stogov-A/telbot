package com.example.telegrambot.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Parser {

    public static final String URL_494 = "https://busti.me/spb/bus-494/?hl=ru";
    public static final String URL_315 = "https://rasp.yandex.ru/station/9837905/?date=all-days";

    private Document getDocumentFromURL(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSchedule315FromBel(){
        Document document = getDocumentFromURL(URL_315);
        try {
            return document.select("div.StationScheduleBlock__tableScheduleColumns").get(0).select("div").get(0).text();
        } catch (Exception e) {
            return null;
        }
    }

    public String getSchedule494FromHouse(){
        Document document = getDocumentFromURL(URL_494);
        try {
            return document.select("div").get(50).select("div").get(0).text();
        } catch (Exception e) {
            return null;
        }
    }
}
