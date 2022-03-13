package ru.platon.bot2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.platon.bot2.repository.UserRepository;
import ru.platon.bot2.service.*;

/* основной компонент где обрабатывается все что приходит в бот */
@Component
@Slf4j
public class Bot extends TelegramLongPollingBot {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CommandHandler commandHandler;
    private final ButtonService buttonService;
    private final SendMessageOperationService sendMessageService;
    private final CallbackQueryHandler callbackQueryHandler;

    public Bot(UserService userService, UserRepository userRepository, CommandHandler commandHandler, ButtonService buttonService, SendMessageOperationService sendMessageService, CallbackQueryHandler callbackQueryHandler) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.commandHandler = commandHandler;
        this.buttonService = buttonService;
        this.sendMessageService = sendMessageService;
        this.callbackQueryHandler = callbackQueryHandler;

        /* чтобы бот запустился */
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (Exception e) {
            log.warn("Error in constructor Bot " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            /* если update имеет сообщение */
            if (update.hasMessage()) {
                /* если в сообщении есть текст и есть сущность например команд при отправке команды */
                if (update.getMessage().hasText() && update.getMessage().hasEntities()){
                    execute(commandHandler.searchForTheDesiredCommand(update));
                } else if (update.getMessage().hasText()){
                    execute(SendMessage.builder()
                        .text("заглушка")
                        .chatId(String.valueOf(update.getMessage().getChatId())).build());
                }
            }
            /* пока не знаю что это */
            else if (update.hasChannelPost()) {

            }
            /* если в обновлении есть информаци о нажатой кнопке */
            else if (update.hasCallbackQuery()) {
                String callDate = update.getCallbackQuery().getData();
                callbackQueryHandler.creatEditMessageText(callDate, update);
            }
        } catch (Exception e) {
            log.warn("Error in onUpdateReceived " + e.getStackTrace());
        }
    }


    @Override
    public String getBotUsername() {
        return "@LanskayBot";
    }

    @Override
    public String getBotToken() {
        return "5253249290:AAE_zb8dax3bRkNnoQmNbu9M2OQObrKdMpw";
    }

    private <T extends BotApiMethod> void executeMessage(T sendMessage) {
        try {
            execute(sendMessage);
        } catch (Exception e) {
            log.warn("Error in executeMessage " + e);
        }
    }

    //                execute(SendMessage.builder()
//                        .chatId(update.getMessage().getChatId().toString())
//                        /* извлекает из message.getText() сообщение которое прислал пользователь и
//                         * отправляет обратно */
//                        .text(restr)
//                        .build()
//                );
}

