package com.example.leo_forum_project_test.repository;

import com.example.leo_forum_project_test.dto.Message;

public interface MessageRepositoryV1 {
    Message createMessage(Message message);

    Message readMessageById(Long messageId);

    Message updateMessage(Long messageId, Message message);

    void deleteMessage(Long messageId);
}

