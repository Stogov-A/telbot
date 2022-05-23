package com.example.telegrambot.service;

import com.example.telegrambot.ResponseMessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.telegrambot.Utils.getTimeFromText;


@RequiredArgsConstructor
@Component
public class TelegramService {

    private static final String FROM_HOME_TO_SESTR = "в сестр";
    private static final String FROM_HOME_TO_BEL = "в бел";
    private static final String START = "/start";

    private final ResponseMessageBuilder responseMessageBuilder;

    public SendMessage startProcess(Update update) {
        String outText = null;
        if (update.hasMessage() && update.getMessage().hasText()) {
            String inputText = update.getMessage().getText().toLowerCase();
            if (inputText.contains(FROM_HOME_TO_SESTR)) {
                outText = responseMessageBuilder.getAnsFromHomeToSestr(getTimeFromText(inputText));
            } else if (inputText.contains(START)) {
                outText = responseMessageBuilder.getAnsFromHomeToSestr(getTimeFromText(inputText));
            } else if (inputText.contains(FROM_HOME_TO_BEL)) {
                outText = responseMessageBuilder.getAnsFromHomeToBel(getTimeFromText(inputText));
            }
            else if (inputText.startsWith("за ")) {
                outText = responseMessageBuilder.getAnsErrReminder();
            }
        }
        return createSendMessage(update, outText);
    }


    public static SendMessage createSendMessage(Update update, String outText) {
        //Извлекаем из объекта сообщение пользователя
        Message inMess = update.getMessage();
        //Достаем из inMess id чата пользователя
        String chatId = inMess.getChatId().toString();
        //Создаем объект класса SendMessage - наш будущий ответ пользователю
        SendMessage outMess = new SendMessage();
        //Добавляем в наше сообщение id чата а также наш ответ
        outMess.setChatId(chatId);
        outMess.setText(outText);
        return outMess;
    }

}
