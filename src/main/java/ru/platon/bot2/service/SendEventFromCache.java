package ru.platon.bot2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.platon.bot2.Bot;
import ru.platon.bot2.repository.AnswerRepository;
import ru.platon.bot2.repository.CompletedQuestionnaireRepository;
import ru.platon.bot2.repository.QuestionRepository;
import ru.platon.bot2.repository.QuestionnaireRepository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class SendEventFromCache {

    @Value("${telegrambot.adminId}")
    private int admin_id;
    @Value("${spring.datasource.driver-class-name}")
    private static String POSTGRESQL_DRIVER;

    private final Bot bot;
    private final AnswerRepository answerRepository;
    private final CompletedQuestionnaireRepository cQRepository;
    private final QuestionnaireRepository qRepository;
    private final QuestionRepository questionRepository;

    public SendEventFromCache(Bot bot, AnswerRepository answerRepository, CompletedQuestionnaireRepository cQRepository, QuestionnaireRepository qRepository, QuestionRepository questionRepository) {
        this.bot = bot;
        this.answerRepository = answerRepository;
        this.cQRepository = cQRepository;
        this.qRepository = qRepository;
        this.questionRepository = questionRepository;
    }

    @PostConstruct
    //after every restart app  - check unspent events
    private void afterStart() {
        try {
//        List<eventcashentity> list = eventCashDAO.findAllEventCash();

//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setChatId(String.valueOf(admin_id));
//            sendMessage.setText("Произошла перезагрузка!");
//            bot.execute(sendMessage);

            /* Если БД пустая */
            if (answerRepository.count() == 0 &&
                    cQRepository.count() == 0 &&
                    qRepository.count() == 0 &&
                    questionRepository.count() == 0) {
                /* то достаем из файла данные и вставляем */
                ConnectBD.connectAndExecute("INSERT INTO answer (id, answer) VALUES (1, 'Рим');");
            }


//        if (!list.isEmpty()) {
//            for (EventCashEntity eventCashEntity : list) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(eventCashEntity.getDate());
//                SendEvent sendEvent = new SendEvent();
//                sendEvent.setSendMessage(new SendMessage(String.valueOf(eventCashEntity.getUserId()), eventCashEntity.getDescription()));
//                sendEvent.setEventCashId(eventCashEntity.getId());
//                new Timer().schedule(new SimpleTask(sendEvent), calendar.getTime());
//            }
//        }
        } catch (Exception e) {
            log.warn(String.valueOf(e));
        }
    }

    private String readerInsertFile() {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(Objects.requireNonNull(getClass().getClassLoader().getResource("insert.txt")).getFile()))) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            log.warn("FileNotFoundException in readerInsertFile" + e.getMessage());
        } catch (IOException e) {
            log.warn("IOException in readerInsertFile" + e.getMessage());
        }
        return stringBuilder.toString();
    }
}
