package ru.platon.bot2.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

/* обработчик команд при нажатии кнопки  */
@Service
public class CallbackQueryHandler {

    public EditMessageText creatEditMessageText(String callDate, Update update){
        EditMessageText editMessageText = new EditMessageText();
        if (callDate.equals("92 бензин")) {

            long mesId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            editMessageText.setChatId(String.valueOf(chatId));
            editMessageText.setMessageId(Math.toIntExact(mesId));
            editMessageText.setText("Вот");
        }
        return editMessageText;
    }
}
