package ru.platon.bot2.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

/* обработчик команд при нажатии кнопки  */
@Service
public class CallbackQueryHandler {

    private final ButtonService buttonService;

    public CallbackQueryHandler(ButtonService buttonService) {
        this.buttonService = buttonService;
    }

    public EditMessageText creatEditMessageText(String callDate, Update update) {
        EditMessageText editMessageText = new EditMessageText();
        long mesId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        editMessageText.setChatId(String.valueOf(chatId));
        editMessageText.setMessageId(Math.toIntExact(mesId));

        if (List.of("Венеция", "Рим", "Казань", "Москва").contains(callDate)) {
            editMessageText.setText("Какую машину Вы бы выбрали?");
            /* добавляем кнопки */
            editMessageText.setReplyMarkup(
                    buttonService.setInlineKeyboardMarkup(
                            buttonService.creatInlineButton(
                                    List.of("Лада", "Ferrari", "Land Rover", "KIA")))
            );
        } else if (List.of("Лада", "Ferrari", "Land Rover", "KIA").contains(callDate)) {
            editMessageText.setText("Какой бензин Вы бы выбрали?");
            /* добавляем кнопки */
            editMessageText.setReplyMarkup(
                    buttonService.setInlineKeyboardMarkup(
                            buttonService.creatInlineButton(
                                    List.of("92", "95", "100", "Ракетное топливо")))
            );
        } else if (List.of("92", "95", "100", "Ракетное топливо").contains(callDate)) {
            editMessageText.setText("Какую скорость Вы бы выбрали?");
            /* добавляем кнопки */
            editMessageText.setReplyMarkup(
                    buttonService.setInlineKeyboardMarkup(
                            buttonService.creatInlineButton(
                                    List.of("60-80", "80-120", "120-140", "140 и выше")))
            );
        } else {
            editMessageText.setText("Конец опроса");
        }
        return editMessageText;
    }
}
