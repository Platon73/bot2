package ru.platon.bot2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.platon.bot2.entities.Du;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/* обработчик команд */
@Service
@Slf4j
public class CommandHandler {

    public SendMessage searchForTheDesiredCommand(Update update){
        /* сообщение, которое вернем */
        SendMessage sendMessage = new SendMessage();
        /* чат куда будем отправлять */
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));

        Message message = update.getMessage();
        /* ищем команду */
        Optional<MessageEntity> commandEntity = message.getEntities().stream()
            /* если среди сущностей найден тип bot_command */
            .filter(el -> "bot_command".equals(el.getType()))
            /* то возвращаем его */
            .findFirst();
        if (commandEntity.isPresent()) {
            /* берем текст сообщения */
            String command = message.getText()
                    /* обрезаем лишний текст */
                    .substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
            /* если command */
            /* соответствует /start_testing */
            if (command.equals("/start_testing")) {/* массив листов кнопок */
//                commandStart_testing(sendMessage, message);
                sendMessage.setText("была нажата команда start_testing");
            } else if (command.equals("/start")){
//                sendingMessage("Вы отправили команду Start", chatId);
            } else {
//                defaultCommand(sendMessage, String.valueOf(update.getMessage().getChatId()));
            }
        }
        return sendMessage;
    }

    private SendMessage defaultCommand(SendMessage sendMessage, String chatId){
        return SendMessage.builder().text("такой команды нет").chatId(chatId).build();
    }

    private void commandSTART(SendMessage sendMessage, Message message){
        sendMessage.setText("Нажали кнопку старт");
    }

    private void commandStart_testing(SendMessage sendMessage, Message message){
        try {
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            /* заполняем массив из двух рядов кнопок */
            for (Du du : Du.values()) {
                buttons.add(Arrays.asList(
                    InlineKeyboardButton.builder()
                        /* текст в кнопке */
                        .text(du.getRusName())
                        /* команда которая вернеться при нажатии кнопки */
                        .callbackData("ORIGINAL: " + Du.ODIN)
                        .build(),
                    InlineKeyboardButton.builder()
                        /* текст в кнопке */
                        .text(du.getRusName())
                        /* команда которая вернеться при нажатии кнопки */
                        .callbackData("TARGET: " + Du.VTOROY)
                        .build()
                ));
            }
            /* отправляем сообщение */
            SendMessage.builder()
                /* текст сообщения */
                .text("Вы нажали команду Start_testing")
                /* в какой чат переслать сообщение */
                .chatId(message.getChatId().toString())
                /* возвращаем кнопку */
                .replyMarkup(
                    InlineKeyboardMarkup.builder()
                        /* сюда положили кнопки */
                        .keyboard(buttons)
                        .build()
                )
                .build();
        }catch (Exception e){
            log.warn("Error in ");
        }
    }

    /**
     * Метод отправляет сообщение
     * @param textMessage текст сообщения
     * @param chatId чат Id куда отправлять
     */
    private void sendingMessage(String textMessage, String chatId){
        /* отправляем сообщение */
        SendMessage.builder()
                /* текст сообщения */
                .text(textMessage)
                /* в какой чат переслать сообщение */
                .chatId(chatId)
                .build();
    }
}
