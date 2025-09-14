package com.example.leo_forum_project_test.repository;

import com.example.leo_forum_project_test.entity.MessageE;

public interface MessageRepositoryV1 {
    MessageE createMessage(MessageE messageE);

    MessageE findMessageById(Long messageId);

    MessageE updateMessage(Long messageId, MessageE messageE);

    void deleteMessage(Long messageId);
}

