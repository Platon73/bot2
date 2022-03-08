package ru.platon.bot2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.platon.bot2.entities.Status;
import ru.platon.bot2.entities.User;
import ru.platon.bot2.repository.UserRepository;
import ru.platon.bot2.service.CommandHandler;
import ru.platon.bot2.service.UserService;

import java.util.Optional;

@Component
@Slf4j
public class Bot extends TelegramLongPollingBot {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CommandHandler commandHandler;

    public Bot(UserService userService, UserRepository userRepository, CommandHandler commandHandler) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.commandHandler = commandHandler;
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
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                if(userService.userRegistered(update.getMessage().getFrom().getId())){
                    User user = new User();
                    user.setId(update.getMessage().getFrom().getId());
                    user.setName(update.getMessage().getFrom().getFirstName());
                    user.setStatus(Status.CUSTOMER);
                    User saveUser = userRepository.save(user);
                    sendMessage.setText("Сохранили как "+saveUser.toString());
                } else {
                    Optional<User> optional = userRepository.findById(update.getMessage().getFrom().getId());
                    if (optional.isPresent()){
                        sendMessage.setText("Вы уже были сохранены как "+optional.get().toString());
                    }
                }
                execute(sendMessage);
//                execute(SendMessage.builder()
//                        .chatId(update.getMessage().getChatId().toString())
//                        /* извлекает из message.getText() сообщение которое прислал пользователь и
//                         * отправляет обратно */
//                        .text("Привет "+ update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getUserName())
//                        .build()
//                );
//                Message message = update.getMessage();
//                Long userId = update.getMessage().getFrom().getId();
//                /* проверяем зарегестрирован ли пользователь */
//                if (userService.userRegistered(userId)) {
//                    /* dfsfs*/
//                    handleMessage(update);
//                    execute(SendMessage.builder()
//                            .chatId(message.getChatId().toString())
//                            /* извлекает из message.getText() сообщение которое прислал пользователь и
//                             * отправляет обратно */
//                            .text("Вы зарегестрированы " + userService.getUesrIsBD(userId).toString())
//                            .build()
//                    );
//                } else {
//                    execute(SendMessage.builder()
//                            .chatId(message.getChatId().toString())
//                            /* извлекает из message.getText() сообщение которое прислал пользователь и
//                             * отправляет обратно */
//                            .text("Вы НЕ зарегестрированы " + update.getMessage().getFrom().getId())
//                            .build()
//                    );
//                }
//                handleMessage(update.getMessage());
//                Message message = update.getMessage();
//                /* если в сообщении есть текст */
//                if (message.hasText()) {
//                    execute(SendMessage.builder()
//                            .chatId(message.getChatId().toString())
//                            /* извлекает из message.getText() сообщение которое прислал пользователь и
//                             * отправляет обратно */
//                            .text("Вы сказали - " + update.getMessage().getFrom().getId())
//                            .build()
//                    );
//                }
            }
        } catch (Exception e) {
            log.warn("Error in onUpdateReceived " + e);
        }
    }

    /**
     * Метод для обработки команд
     *
     * @param update обновление от чат бота
     */
    private void handleMessage(Update update) {
        try {
            /* если в сообщении есть текст и есть сущьность например команд при отправке команды */
            if (update.getMessage().hasText() && update.getMessage().hasEntities()) {
//                execute(commandHandler.searchForTheDesiredCommand(update).b);
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Tesn");
                sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                execute(sendMessage);
            }
        } catch (Exception e) {
            log.warn("Error in handleMessage "+ e.getMessage());
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
}

