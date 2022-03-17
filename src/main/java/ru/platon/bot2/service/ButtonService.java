package ru.platon.bot2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ButtonService {

    public List<List<InlineKeyboardButton>> creatInlineButton(List<String> listString){
        List<List<InlineKeyboardButton>> inlineKeyboardButtonList = new ArrayList<>();
        /* ряд кнопок */
        List<InlineKeyboardButton> inlineKeyboardButtonRow = new ArrayList<>();

        for (String testButton : listString) {
            /* Сама кнопка */
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            /* текст который содержится в кнопке */
            inlineKeyboardButton.setText(testButton);
            /* сообщение, которое возвращает телеграмм при нажатии этой кнопки */
            inlineKeyboardButton.setCallbackData(testButton);

            inlineKeyboardButtonRow.add(inlineKeyboardButton);
        }

        inlineKeyboardButtonList.add(inlineKeyboardButtonRow);
        return inlineKeyboardButtonList;
    }

    public InlineKeyboardMarkup setInlineKeyboardMarkup(List<List<InlineKeyboardButton>> listButtton){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(listButtton);
        return inlineKeyboardMarkup;
    }
}
