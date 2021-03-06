package ru.platon.bot2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.platon.bot2.Bot;
import ru.platon.bot2.entities.questionnaire.Answer;
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

/* класс отвечает за запуск алгоритмов при запуске и перезапуске приложения */
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
    private final QuestionnaireRepository questionnaireRep;
    private final QuestionRepository questionRepository;
    private final FillingBD fillingBD;

    public SendEventFromCache(Bot bot, AnswerRepository answerRepository, CompletedQuestionnaireRepository cQRepository, QuestionnaireRepository qRepository, QuestionRepository questionRepository, FillingBD fillingBD) {
        this.bot = bot;
        this.answerRepository = answerRepository;
        this.cQRepository = cQRepository;
        this.questionnaireRep = qRepository;
        this.questionRepository = questionRepository;
        this.fillingBD = fillingBD;
    }

    /* после каждого перезапуска приложения - проверять неизрасходованные события */
    @PostConstruct
    private void afterStart() {
        try {
//        List<eventcashentity> list = eventCashDAO.findAllEventCash();
            StringBuilder textAdmin = new StringBuilder("Произошла перезагрузка!\n");
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(admin_id));


            /* Если список ответов пустая */
            if (answerRepository.count() == 0 ) {
                /* то достаем из файла данные и вставляем */
                fillingBD.fillingAnswer();
                textAdmin.append("Вставлены ответы\n");
            }
            if (questionRepository.count() == 0){
                fillingBD.fillingQuestion();
                textAdmin.append("Вставлены вопросы\n");
            }
            if (questionnaireRep.count() == 0 ){
                fillingBD.fillingQuestionnaire();
                textAdmin.append("Вставлены опросники\n");
            }
            if (cQRepository.count() == 0){

            }


            /* отправка отчета админу после запуска приложения */
            sendMessage.setText(textAdmin.toString());
            bot.execute(sendMessage);


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

    private void readerInsertFile(String nameFile) {
        try (BufferedReader reader = new BufferedReader(
            new FileReader(
                Objects.requireNonNull(getClass().getClassLoader()
                    .getResource("dataBD.txt")).getFile()))) {
            String line = reader.readLine();
            while (line != null) {
                String[] arr = line.split(",");
                answerRepository.save(new Answer(Long.valueOf(arr[0]), arr[1]));
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            log.warn("FileNotFoundException in readerInsertFile" + e.getMessage());
        } catch (IOException e) {
            log.warn("IOException in readerInsertFile" + e.getMessage());
        }
    }

    /**
     * Метод заполняет БД данными из строк файла
     */
    private void fillingBD(){

    }
}
