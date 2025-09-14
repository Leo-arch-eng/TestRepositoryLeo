package com.example.leo_forum_project_test.repository.mock;

import com.example.leo_forum_project_test.entity.MessageE;
import com.example.leo_forum_project_test.repository.MessageRepositoryV1;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class MessageRepositoryV1Mock implements MessageRepositoryV1 {

    private final Map<Long, MessageE> messageMap;
    private static Long lastCreatedMessageId = 0L;

    MessageRepositoryV1Mock() {
        this.messageMap = new HashMap();
    }


    @Override
    public MessageE createMessage(MessageE messageE) {
        Long newMessageId = lastCreatedMessageId++;
        messageE.setMessageId(newMessageId);
        messageMap.put(newMessageId, messageE);
        System.out.println(
                "Было создано новое сообщение от пользователя c :" +
                        "NAME" + "и c ID: " + newMessageId
        );
        return messageE;
    }

    @Override
    public MessageE findMessageById(Long messageId) {
        ;
        System.out.println("Выбрано сообщение с ID: " + messageId);
        return messageMap.get(messageId);
    }

    @Override
    public MessageE updateMessage(Long messageId, MessageE messageE
    ) {
        if (messageMap.containsKey(messageId)) {
            messageMap.put(messageId, messageE);
            System.out.println("Сообщение с идентификатором: " + messageId + " было обновлено");
        }
        return messageE;
    }

    @Override
    public void deleteMessage(Long messageId) {
        if(messageMap.remove(messageId) != null) {
            System.out.println("Было удалено сообщение с ID: " + messageId);
        }else {
            System.out.println("Сообщение с ID: " + messageId + " не было найдено");
        }
    }
}
