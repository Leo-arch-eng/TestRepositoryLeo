package com.example.leo_forum_project_test.service.mock;

import com.example.leo_forum_project_test.entity.TopicE;
import com.example.leo_forum_project_test.repository.TopicRepositoryV1;
import com.example.leo_forum_project_test.validator.TopicValidatorV1;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TopicEDtoServiceV1MockTest {

    @Mock
    private TopicRepositoryV1 topicRepositoryV1;

    @Mock
    private TopicValidatorV1 topicValidatorV1;

    @InjectMocks
    private TopicServiceV1Mock topicService;

    private TopicE sampleTopicE;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleTopicE = new TopicE();
        sampleTopicE.setId(1L);
    }

    //Тесты create

    @Test
    public void testCreateSuccess() {
        when(topicValidatorV1.validateCreate(any())).thenReturn(Collections.emptyList());
        when(topicRepositoryV1.create(any())).thenReturn(sampleTopicE);

        TopicE result = topicService.create(sampleTopicE);

        assertNotNull(result);
        assertEquals(sampleTopicE.getId(), result.getId());
        verify(topicValidatorV1).validateCreate(any());
        verify(topicRepositoryV1).create(any());
    }

    @Test
    public void testCreateValidationError() {
        List<String> errors = List.of("Error 1");
        when(topicValidatorV1.validateCreate(any())).thenReturn(errors);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            topicService.create(sampleTopicE);
        });

        assertTrue(exception.getMessage().contains("Error 1"));
        verify(topicValidatorV1).validateCreate(any());
        verifyNoInteractions(topicRepositoryV1);
    }

    // Тесты findById

    @Test
    public void testFindByIdSuccess() {
        when(topicValidatorV1.validateFindById(anyLong())).thenReturn(Collections.emptyList());
        when(topicRepositoryV1.findById(anyLong())).thenReturn(sampleTopicE);

        TopicE result = topicService.findById(1L);

        assertNotNull(result);
        assertEquals(sampleTopicE.getId(), result.getId());
        verify(topicValidatorV1).validateFindById(anyLong());
        verify(topicRepositoryV1).findById(anyLong());
    }

    @Test
    public void testFindByIdValidationError() {
        List<String> errors = List.of("Invalid ID");
        when(topicValidatorV1.validateFindById(anyLong())).thenReturn(errors);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            topicService.findById(999L);
        });

        assertTrue(exception.getMessage().contains("Invalid ID"));
        verify(topicValidatorV1).validateFindById(anyLong());
        verifyNoMoreInteractions(topicRepositoryV1);
    }

    // Тесты updateById

    @Test
    public void testUpdateByIdSuccess() {
        when(topicValidatorV1.validateUpdateById(anyLong(), any())).thenReturn(Collections.emptyList());
        when(topicRepositoryV1.update(anyLong(), any())).thenReturn(sampleTopicE);

        TopicE result = topicService.updateById(1L, sampleTopicE);

        assertNotNull(result);
        assertEquals(sampleTopicE.getId(), result.getId());
        verify(topicValidatorV1).validateUpdateById(anyLong(), any());
        verify(topicRepositoryV1).update(anyLong(), any());
    }

    @Test
    public void testUpdateByIdValidationError() {
        List<String> errors = List.of("Update error");
        when(topicValidatorV1.validateUpdateById(anyLong(), any())).thenReturn(errors);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            topicService.updateById(2L, sampleTopicE);
        });

        assertTrue(exception.getMessage().contains("Update error"));
        verify(topicValidatorV1).validateUpdateById(anyLong(), any());
        verifyNoMoreInteractions(topicRepositoryV1);
    }

    @Test
    public void testUpdateByIdNotFound() {
        when(topicValidatorV1.validateUpdateById(anyLong(), any())).thenReturn(Collections.emptyList());
        when(topicRepositoryV1.update(anyLong(), any())).thenReturn(null); // не найдено

        TopicE result = topicService.updateById(999L, sampleTopicE);

        assertNull(result);
        verify(topicRepositoryV1).update(anyLong(), any());
    }

    // Тесты deleteById

    @Test
    public void testDeleteByIdSuccess() {
        when(topicValidatorV1.validateDeleteById(anyLong())).thenReturn(Collections.emptyList());

        doNothing().when(topicRepositoryV1).deleteById(anyLong());


        assertDoesNotThrow(() -> topicService.deleteById(3L));

        verify(topicValidatorV1).validateDeleteById(3L);
        verify(topicRepositoryV1).deleteById(3L);
    }

    @Test
    public void testDeleteByIdValidationError() {
        List<String> errors = List.of("Cannot delete");
        when(topicValidatorV1.validateDeleteById(anyLong())).thenReturn(errors);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            topicService.deleteById(4L);
        });

        assertTrue(exception.getMessage().contains("Cannot delete"));
        verify(topicValidatorV1).validateDeleteById(4L);
        verifyNoMoreInteractions(topicRepositoryV1);
    }

    @Test
    public void testDeleteByIdExceptionDuringDeletion() {
        when(topicValidatorV1.validateDeleteById(anyLong())).thenReturn(Collections.emptyList());

        doThrow(new RuntimeException("DB error")).when(topicRepositoryV1).deleteById(anyLong());

        assertThrows(RuntimeException.class, () -> {
            topicService.deleteById(4L);
        });

        verify(topicValidatorV1).validateDeleteById(4L);
    }
}