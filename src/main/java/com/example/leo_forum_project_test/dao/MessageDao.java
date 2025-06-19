package com.example.leo_forum_project_test.dao;

import com.example.leo_forum_project_test.entity.Message;

import java.util.List;

public interface MessageDao {

    public List<Message> insertMessages(Message message);

    public List<Message> getAllMessages();

    public int getMessageById(int id);

    public boolean deleteMessageById(int id);
}
