package com.example.leo_forum_project_test.service;

import com.example.leo_forum_project_test.dto.MessageCompositeDto;
import com.example.leo_forum_project_test.dto.MessageDto;
import com.example.leo_forum_project_test.dto.TopicCompositeDto;
import jakarta.validation.Valid;

import java.util.List;

public interface MessageServiceV1 {
    MessageDto createMessage(@Valid MessageDto messageDto);

    MessageDto findMessageById(Long messageId);

    MessageDto updateMessage(Long messageId,@Valid MessageDto messageDto);

    void deleteMessage(Long messageId);

    List<MessageDto> findAllMessagesPaginated(int page, int size, String sortBy, String sortDir);

    MessageCompositeDto getMessageWithComment(Long messageId);
}
