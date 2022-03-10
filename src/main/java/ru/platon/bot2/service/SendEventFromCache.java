package ru.platon.bot2.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.platon.bot2.Bot;

import javax.annotation.PostConstruct;
import java.io.*;

@Component
@Slf4j
public class SendEventFromCache {

    @Value("${telegrambot.adminId}")
    private int admin_id;

    private final Bot bot;

    public SendEventFromCache(Bot bot) {
        this.bot = bot;
    }

    @PostConstruct
    //after every restart app  - check unspent events
    private void afterStart() {
        try {
//        List<eventcashentity> list = eventCashDAO.findAllEventCash();

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(admin_id));
            sendMessage.setText("Произошла перезагрузка!");
            bot.execute(sendMessage);

            try {
                File file = new File("scr/main/resources/data.sql");
                //создаем объект FileReader для объекта File
                FileReader fr = new FileReader(file);
                //создаем BufferedReader с существующего FileReader для построчного считывания
                BufferedReader reader = new BufferedReader(fr);
                // считаем сначала первую строку
                String line = reader.readLine();
                while (line != null) {
                    System.out.println("строка в файле "+line);
                    // считываем остальные строки в цикле
                    line = reader.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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
        } catch (Exception e){
            log.warn(String.valueOf(e));
        }
    }
}
