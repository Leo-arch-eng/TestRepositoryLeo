package com.example.leo_forum_project_test.service.mock;

import com.example.leo_forum_project_test.entity.MessageE;
import com.example.leo_forum_project_test.repository.MessageRepositoryV1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MessageEDtoServiceV1MockTest {

    @Mock
    private MessageRepositoryV1 messageRepositoryV1;

    @InjectMocks
    private MessageServiceV1Mock messageService;

    private MessageE sampleMessageE;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleMessageE = new MessageE();
        sampleMessageE.setMessageId(1L);
        sampleMessageE.setAuthorName("Иван");
        sampleMessageE.setAuthorSurname("Петров");
        sampleMessageE.setMessage("Это тестовое сообщение");
    }

    @Test
    public void testCreateMessageSuccess() {
        when(messageRepositoryV1.createMessage(any(MessageE.class))).thenReturn(sampleMessageE);

        MessageE result = messageService.createMessage(sampleMessageE);

        assertNotNull(result);
        assertEquals("Иван", result.getAuthorName());
        verify(messageRepositoryV1).createMessage(any(MessageE.class));
    }

    @Test
    public void testCreateMessageException() {
        when(messageRepositoryV1.createMessage(any(MessageE.class))).thenThrow(new RuntimeException("DB error"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            messageService.createMessage(sampleMessageE);
        });

        assertEquals("DB error", thrown.getMessage());
        verify(messageRepositoryV1).createMessage(any(MessageE.class));
    }

    @Test
    public void testFindMessageByIdSuccess() {
        when(messageRepositoryV1.findMessageById(anyLong())).thenReturn(sampleMessageE);

        MessageE result = messageService.findMessageById(1L);

        assertNotNull(result);
        assertEquals("Иван", result.getAuthorName());
        verify(messageRepositoryV1).findMessageById(1L);
    }

    @Test
    public void testFindMessageByIdNotFound() {
        when(messageRepositoryV1.findMessageById(anyLong())).thenReturn(null);

        MessageE result = messageService.findMessageById(999L);

        assertNull(result);
        verify(messageRepositoryV1).findMessageById(999L);
    }

    @Test
    public void testUpdateMessageSuccess() {
        MessageE updatedMessageE = new MessageE();
        updatedMessageE.setMessageId(1L);
        updatedMessageE.setAuthorName("Петр");
        updatedMessageE.setAuthorSurname("Иванов");
        updatedMessageE.setMessage("Обновленное сообщение");

        when(messageRepositoryV1.updateMessage(eq(1L), any(MessageE.class))).thenReturn(updatedMessageE);

        MessageE result = messageService.updateMessage(1L, updatedMessageE);

        assertNotNull(result);
        assertEquals("Петр", result.getAuthorName());
        verify(messageRepositoryV1).updateMessage(eq(1L), any(MessageE.class));
    }

    @Test
    public void testUpdateMessageException() {
        when(messageRepositoryV1.updateMessage(eq(1L), any(MessageE.class)))
                .thenThrow(new RuntimeException("DB error"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            messageService.updateMessage(1L, sampleMessageE);
        });

        assertEquals("DB error", thrown.getMessage());
        verify(messageRepositoryV1).updateMessage(eq(1L), any(MessageE.class));
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