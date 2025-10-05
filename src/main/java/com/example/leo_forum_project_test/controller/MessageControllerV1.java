package com.example.leo_forum_project_test.controller;

import com.example.leo_forum_project_test.dto.MessageDto;
import com.example.leo_forum_project_test.service.MessageServiceV1;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/topic/message")
public class MessageControllerV1 {

    private final MessageServiceV1 messageService;

    public MessageControllerV1(MessageServiceV1 messageService) {
        this.messageService = messageService;
    }

    // Создать новое сообщение
    @PostMapping
    public ResponseEntity<MessageDto> createMessage(@RequestBody MessageDto messageDto) {
        MessageDto created = messageService.createMessage(messageDto);
        return ResponseEntity.ok(created);
    }

    // Получить сообщение по ID
    @GetMapping("/{id}")
    public ResponseEntity<MessageDto> getMessage(@PathVariable Long id) {
        MessageDto messageDto = messageService.findMessageById(id);
        return ResponseEntity.ok(messageDto);
    }

    // Обновить сообщение
    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> updateMessage(@PathVariable Long id, @RequestBody MessageDto messageDto) {
        MessageDto updated = messageService.updateMessage(id, messageDto);
        return ResponseEntity.ok(updated);
    }

    // Удалить сообщение
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}