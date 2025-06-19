package com.example.leo_forum_project_test.dao;


import com.example.leo_forum_project_test.entity.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageDaoImpl implements MessageDao {

    @Override
    public List<Message> insertMessages(Message message) {
        return List.of(); // метод для ввода сообщений от пользователей
    }

    @Override
    public List<Message> getAllMessages() {
        return List.of(); //метод для оторбажения всех введенных сообщений на экране
    }

    @Override
    public int getMessageById(int id) {
        return 0; // метод для поиска сообщений по ИМЕНИ пользователя, а id - это внешний ключ для таблицы авторы,
        //привязынный к имени автора

    }

    @Override
    public boolean deleteMessageById(int id) {
        return false; // методя для удаления сообщения по Имени пользователя
    }
}
