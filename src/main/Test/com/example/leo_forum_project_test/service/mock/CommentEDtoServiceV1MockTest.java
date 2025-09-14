package com.example.leo_forum_project_test.service.mock;

import com.example.leo_forum_project_test.entity.CommentE;
import com.example.leo_forum_project_test.repository.CommentRepositoryV1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentEDtoServiceV1MockTest {

    @Mock
    private CommentRepositoryV1 commentRepositoryV1;

    @InjectMocks
    private CommentServiceV1Mock commentService;

    private CommentE sampleCommentE;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Создаем валидный комментарий
        sampleCommentE = new CommentE();
        sampleCommentE.setComment("Это тестовый комментарий");
        sampleCommentE.setAuthor("Иван");
        sampleCommentE.setId(1L);
    }

    @Test
    public void testCreateComment_Success() {
        when(commentRepositoryV1.createComment(any(CommentE.class))).thenReturn(sampleCommentE);

        CommentE result = commentService.createComment(sampleCommentE);

        assertNotNull(result);
        assertEquals("Иван", result.getAuthor());
        verify(commentRepositoryV1).createComment(any(CommentE.class));
    }

    @Test
    public void testCreateComment_Exception() {
        when(commentRepositoryV1.createComment(any(CommentE.class)))
                .thenThrow(new RuntimeException("DB error"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            commentService.createComment(sampleCommentE);
        });

        assertEquals("DB error", thrown.getMessage());
        verify(commentRepositoryV1).createComment(any(CommentE.class));
    }

    @Test
    public void testGetCommentById_Success() {
        when(commentRepositoryV1.getCommentById(anyLong())).thenReturn(sampleCommentE);

        CommentE result = commentService.getCommentById(1L);

        assertNotNull(result);
        assertEquals("Иван", result.getAuthor());
        verify(commentRepositoryV1).getCommentById(1L);
    }

    @Test
    public void testGetCommentById_NotFound() {
        when(commentRepositoryV1.getCommentById(anyLong())).thenReturn(null);

        CommentE result = commentService.getCommentById(999L);

        assertNull(result);
        verify(commentRepositoryV1).getCommentById(999L);
    }

    @Test
    public void testUpdateCommentById_Success() {
        CommentE updatedCommentE = new CommentE();
        updatedCommentE.setComment("Обновленный комментарий");
        updatedCommentE.setAuthor("Петр");
        updatedCommentE.setId(1L);

        when(commentRepositoryV1.updateCommentById(eq(1L), any(CommentE.class)))
                .thenReturn(updatedCommentE);

        CommentE result = commentService.updateCommentById(1L, updatedCommentE);

        assertNotNull(result);
        assertEquals("Петр", result.getAuthor());
        verify(commentRepositoryV1).updateCommentById(eq(1L), any(CommentE.class));
    }

    @Test
    public void testUpdateCommentById_NotFound() {
        when(commentRepositoryV1.updateCommentById(eq(999L), any(CommentE.class)))
                .thenReturn(null);

        CommentE result = commentService.updateCommentById(999L, sampleCommentE);

        assertNull(result);
        verify(commentRepositoryV1).updateCommentById(eq(999L), any(CommentE.class));
    }

    @Test
    public void testDeleteCommentById_Success() {
        doNothing().when(commentRepositoryV1).deleteCommentById(anyLong());

        assertDoesNotThrow(() -> commentService.deleteCommentById(1L));
        verify(commentRepositoryV1).deleteCommentById(1L);
    }

    @Test
    public void testDeleteCommentById_Exception() {
        doThrow(new RuntimeException("DB delete error")).when(commentRepositoryV1).deleteCommentById(anyLong());

        assertDoesNotThrow(() -> commentService.deleteCommentById(1L));
        verify(commentRepositoryV1).deleteCommentById(1L);
    }
}