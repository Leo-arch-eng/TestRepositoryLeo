package com.example.leo_forum_project_test.service;

import com.example.leo_forum_project_test.dto.MessageDto;
import jakarta.validation.Valid;

public interface MessageServiceV1 {
    MessageDto createMessage(@Valid MessageDto messageDto);

    MessageDto findMessageById(Long messageId);

    MessageDto updateMessage(Long messageId,@Valid MessageDto messageDto);

    void deleteMessage(Long messageId);
}
