package ru.platon.bot2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Slf4j
public class SendMessageOperationService {

    /**
     * Метод для обработки команд
     *
     * @param update обновление от чат бота
     */
    public SendMessage handleMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        try {
            /* если в сообщении есть текст и есть сущьность например команд при отправке команды */
            if (update.getMessage().hasText() && update.getMessage().hasEntities()) {
//                execute(commandHandler.searchForTheDesiredCommand(update).b);

                sendMessage.setText("Tesn");
            }
        } catch (Exception e) {
            log.warn("Error in handleMessage " + e.getMessage());
            sendMessage.setText("Error");
        }
        return sendMessage;
    }
}
