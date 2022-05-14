package com.example.telegrambot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class Parser {
    private static final String URL = "http://orgp.spb.ru/raspisanie/1850/";

    private Document getDoc() {
        try {
            return Jsoup.connect(URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String ger() {
        Document document = getDoc();
        Element d = Objects.requireNonNull(getDoc()).getElementById("scheduleTable");
        return "";
    }
}
