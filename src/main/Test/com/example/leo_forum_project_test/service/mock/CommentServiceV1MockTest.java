package com.example.leo_forum_project_test.service.mock;

import com.example.leo_forum_project_test.dto.Comment;
import com.example.leo_forum_project_test.repository.CommentRepositoryV1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentServiceV1MockTest {

    @Mock
    private CommentRepositoryV1 commentRepositoryV1;

    @InjectMocks
    private CommentServiceV1Mock commentService;

    private Comment sampleComment;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Создаем валидный комментарий
        sampleComment = new Comment();
        sampleComment.setComment("Это тестовый комментарий");
        sampleComment.setAuthor("Иван");
        sampleComment.setDate("2025-09-02T12:00:00");
        sampleComment.setCommentId(1L);
    }

    @Test
    public void testCreateComment_Success() {
        when(commentRepositoryV1.createComment(any(Comment.class))).thenReturn(sampleComment);

        Comment result = commentService.createComment(sampleComment);

        assertNotNull(result);
        assertEquals("Иван", result.getAuthor());
        verify(commentRepositoryV1).createComment(any(Comment.class));
    }

    @Test
    public void testCreateComment_Exception() {
        when(commentRepositoryV1.createComment(any(Comment.class)))
                .thenThrow(new RuntimeException("DB error"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            commentService.createComment(sampleComment);
        });

        assertEquals("DB error", thrown.getMessage());
        verify(commentRepositoryV1).createComment(any(Comment.class));
    }

    @Test
    public void testGetCommentById_Success() {
        when(commentRepositoryV1.getCommentById(anyLong())).thenReturn(sampleComment);

        Comment result = commentService.getCommentById(1L);

        assertNotNull(result);
        assertEquals("Иван", result.getAuthor());
        verify(commentRepositoryV1).getCommentById(1L);
    }

    @Test
    public void testGetCommentById_NotFound() {
        when(commentRepositoryV1.getCommentById(anyLong())).thenReturn(null);

        Comment result = commentService.getCommentById(999L);

        assertNull(result);
        verify(commentRepositoryV1).getCommentById(999L);
    }

    @Test
    public void testUpdateCommentById_Success() {
        Comment updatedComment = new Comment();
        updatedComment.setComment("Обновленный комментарий");
        updatedComment.setAuthor("Петр");
        updatedComment.setDate("2025-09-03T15:30:00");
        updatedComment.setCommentId(1L);

        when(commentRepositoryV1.updateCommentById(eq(1L), any(Comment.class)))
                .thenReturn(updatedComment);

        Comment result = commentService.updateCommentById(1L, updatedComment);

        assertNotNull(result);
        assertEquals("Петр", result.getAuthor());
        verify(commentRepositoryV1).updateCommentById(eq(1L), any(Comment.class));
    }

    @Test
    public void testUpdateCommentById_NotFound() {
        when(commentRepositoryV1.updateCommentById(eq(999L), any(Comment.class)))
                .thenReturn(null);

        Comment result = commentService.updateCommentById(999L, sampleComment);

        assertNull(result);
        verify(commentRepositoryV1).updateCommentById(eq(999L), any(Comment.class));
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
        // В методе сервиса исключение ловится и логируется, но не пробрасывается
        // Поэтому тестируем, что исключение не пробрасывается
        assertDoesNotThrow(() -> commentService.deleteCommentById(1L));
        verify(commentRepositoryV1).deleteCommentById(1L);
    }
}