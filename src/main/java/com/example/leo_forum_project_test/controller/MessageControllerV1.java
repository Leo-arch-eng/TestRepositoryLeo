package com.example.leo_forum_project_test.controller;

import com.example.leo_forum_project_test.dto.Message;
import com.example.leo_forum_project_test.service.MessageServiceV1;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/topic/message")
public class MessageControllerV1 {

    @Autowired
    private final MessageServiceV1 messageServiceV1;

    public MessageControllerV1(MessageServiceV1 messageServiceV1) {
        this.messageServiceV1 = messageServiceV1;
    }

    @PostMapping
    public Message createMessage(
            @Valid
            @RequestBody Message message
    ){
        Message createdMessage = messageServiceV1.createMessage(message);
        return createdMessage;
    }

    @GetMapping("/{message_id}")
    public Message readMessageById(
            @PathVariable(name = "message_id")
            @Min(0)
            @Max(100) Long messageId
    ){
        Message message = messageServiceV1.findMessageById(messageId);
        return message;
    }

    @PutMapping("/{message_id}")
    public Message updateMessageById(
            @Valid
            @PathVariable(name = "message_id")
            @Min(0)
            @Max(100) Long messageId,
            @RequestBody Message message
    ){
        Message updatedMessage = messageServiceV1.updateMessage(messageId, message);
        return updatedMessage;

    }

    @DeleteMapping("/{message_id}")
    public void deleteMessageById(
            @PathVariable(name = "message_id")
            @Min(0)
            @Max(100) Long messageId
    ){
        messageServiceV1.deleteMessage(messageId);
    }
}
