package com.example.leo_forum_project_test.controller;

import com.example.leo_forum_project_test.dto.CommentDto;
import com.example.leo_forum_project_test.dto.MessageDto;
import com.example.leo_forum_project_test.service.MessageServiceV1;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topic/message")
public class MessageControllerV1 {

    private final MessageServiceV1 messageServiceV1;

    public MessageControllerV1(MessageServiceV1 messageServiceV1) {
        this.messageServiceV1 = messageServiceV1;
    }

    // Создать новое сообщение
    @PostMapping
    public ResponseEntity<MessageDto> createMessage(@RequestBody MessageDto messageDto) {
        MessageDto created = messageServiceV1.createMessage(messageDto);
        return ResponseEntity.ok(created);
    }

    // Получить сообщение по ID
    @GetMapping("/{id}")
    public ResponseEntity<MessageDto> getMessage(@PathVariable Long id) {
        MessageDto messageDto = messageServiceV1.findMessageById(id);
        return ResponseEntity.ok(messageDto);
    }

    // Обновить сообщение
    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> updateMessage(@PathVariable Long id, @RequestBody MessageDto messageDto) {
        MessageDto updated = messageServiceV1.updateMessage(id, messageDto);
        return ResponseEntity.ok(updated);
    }

    // Удалить сообщение
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageServiceV1.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/allMessage")
    public ResponseEntity<List<MessageDto>> getMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "authorName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        try {
            List<MessageDto> messages = messageServiceV1.findAllMessagesPaginated(page, size, sortBy, sortDir);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
