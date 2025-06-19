package com.example.leo_forum_project_test.service;

import com.example.leo_forum_project_test.dto.Message;

public interface MessageServiceV1 {
    Message createMessage(Message message);

    Message readMessageById(Long messageId);

    Message updateMessage(Long messageId, Message message);

    void deleteMessage(Long messageId);
}
