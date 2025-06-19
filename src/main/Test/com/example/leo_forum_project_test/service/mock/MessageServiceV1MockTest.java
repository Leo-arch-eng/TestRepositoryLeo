package com.example.leo_forum_project_test.service.mock;

import com.example.leo_forum_project_test.dto.Message;
import com.example.leo_forum_project_test.repository.MessageRepositoryV1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MessageServiceV1MockTest {

    @Mock
    private MessageRepositoryV1 messageRepositoryV1;

    @InjectMocks
    private MessageServiceV1Mock messageService;

    private Message sampleMessage;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleMessage = new Message();
        sampleMessage.setMessageId(1L);
        sampleMessage.setAuthorName("Иван");
        sampleMessage.setAuthorSurname("Петров");
        sampleMessage.setMessage("Это тестовое сообщение");
        sampleMessage.setLocalDate(20250902L); // пример даты в формате ГГГГММДД
    }

    @Test
    public void testCreateMessageSuccess() {
        when(messageRepositoryV1.createMessage(any(Message.class))).thenReturn(sampleMessage);

        Message result = messageService.createMessage(sampleMessage);

        assertNotNull(result);
        assertEquals("Иван", result.getAuthorName());
        verify(messageRepositoryV1).createMessage(any(Message.class));
    }

    @Test
    public void testCreateMessageException() {
        when(messageRepositoryV1.createMessage(any(Message.class))).thenThrow(new RuntimeException("DB error"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            messageService.createMessage(sampleMessage);
        });

        assertEquals("DB error", thrown.getMessage());
        verify(messageRepositoryV1).createMessage(any(Message.class));
    }

    @Test
    public void testFindMessageByIdSuccess() {
        when(messageRepositoryV1.findMessageById(anyLong())).thenReturn(sampleMessage);

        Message result = messageService.findMessageById(1L);

        assertNotNull(result);
        assertEquals("Иван", result.getAuthorName());
        verify(messageRepositoryV1).findMessageById(1L);
    }

    @Test
    public void testFindMessageByIdNotFound() {
        when(messageRepositoryV1.findMessageById(anyLong())).thenReturn(null);

        Message result = messageService.findMessageById(999L);

        assertNull(result);
        verify(messageRepositoryV1).findMessageById(999L);
    }

    @Test
    public void testUpdateMessageSuccess() {
        Message updatedMessage = new Message();
        updatedMessage.setMessageId(1L);
        updatedMessage.setAuthorName("Петр");
        updatedMessage.setAuthorSurname("Иванов");
        updatedMessage.setMessage("Обновленное сообщение");
        updatedMessage.setLocalDate(20250903L);

        when(messageRepositoryV1.updateMessage(eq(1L), any(Message.class))).thenReturn(updatedMessage);

        Message result = messageService.updateMessage(1L, updatedMessage);

        assertNotNull(result);
        assertEquals("Петр", result.getAuthorName());
        verify(messageRepositoryV1).updateMessage(eq(1L), any(Message.class));
    }

    @Test
    public void testUpdateMessageException() {
        when(messageRepositoryV1.updateMessage(eq(1L), any(Message.class)))
                .thenThrow(new RuntimeException("DB error"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            messageService.updateMessage(1L, sampleMessage);
        });

        assertEquals("DB error", thrown.getMessage());
        verify(messageRepositoryV1).updateMessage(eq(1L), any(Message.class));
    }

    @Test
    public void testDeleteMessageSuccess() {
        doNothing().when(messageRepositoryV1).deleteMessage(anyLong());

        assertDoesNotThrow(() -> messageService.deleteMessage(1L));
        verify(messageRepositoryV1).deleteMessage(1L);
    }

    @Test
    public void testDeleteMessageException() {
        doThrow(new RuntimeException("DB delete error")).when(messageRepositoryV1).deleteMessage(anyLong());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            messageService.deleteMessage(1L);
        });

        assertEquals("DB delete error", thrown.getMessage());
        verify(messageRepositoryV1).deleteMessage(1L);
    }
}