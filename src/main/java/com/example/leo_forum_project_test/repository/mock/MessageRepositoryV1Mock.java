package com.example.leo_forum_project_test.repository.mock;

import com.example.leo_forum_project_test.dto.Message;
import com.example.leo_forum_project_test.repository.MessageRepositoryV1;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class MessageRepositoryV1Mock implements MessageRepositoryV1 {

    private final Map<Long, Message> messageMap;
    private static Long lastCreatedMessageId = 0L;

    MessageRepositoryV1Mock() {
        this.messageMap = new HashMap();
    }


    @Override
    public Message createMessage(Message message) {
        Long newMessageId = lastCreatedMessageId++;
        message.setMessageId(newMessageId);
        messageMap.put(newMessageId, message);
        System.out.println(
                "Было создано новое сообщение от пользователя c :" +
                        "NAME" + "и c ID: " + newMessageId
        );
        return message;
    }

    @Override
    public Message readMessageById(Long messageId) {
        ;
        System.out.println("Выбрано сообщение с ID: " + messageId);
        return messageMap.get(messageId);
    }

    @Override
    public Message updateMessage(Long messageId, Message message
    ) {
        if (!messageMap.containsKey(messageId)) {
            messageMap.put(messageId, message);
            System.out.println("Сообщение с идентификатором: " + messageId + " был обновлено");
        }
        return message;
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
