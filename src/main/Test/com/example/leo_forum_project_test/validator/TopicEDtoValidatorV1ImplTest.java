package com.example.leo_forum_project_test.validator;

import com.example.leo_forum_project_test.entity.TopicE;
import com.example.leo_forum_project_test.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TopicEDtoValidatorV1ImplTest {

    private TopicValidatorV1Impl topicValidatorV1;

    @BeforeEach
    public void setUp() {
        topicValidatorV1 = new TopicValidatorV1Impl();
    }

    //Тесты для validateCreate

    @Test
    public void testValidateCreateValidTopic() {
        TopicE topicE = new TopicE();
        topicE.setTitle("Valid Title");
        List<String> errors = topicValidatorV1.validateCreate(topicE);
        assertTrue(errors.isEmpty(), "Ошибки должны отсутствовать");
    }

    @Test
    public void testValidateCreateNullTopic() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            topicValidatorV1.validateCreate(null);
        });
        assertEquals("Топик не должен быть пустым", exception.getMessage());
    }

    @Test
    public void testValidateCreateTitleNull() {
        TopicE topicE = new TopicE();
        topicE.setTitle(null);
        Exception exception = assertThrows(ValidationException.class, () -> {
            topicValidatorV1.validateCreate(topicE);
        });
        assertEquals("Топик не должен быть пустым или содержать только пробелы", exception.getMessage());
    }

    @Test
    public void testValidateCreateTitleEmpty() {
        TopicE topicE = new TopicE();
        topicE.setTitle("   ");
        Exception exception = assertThrows(ValidationException.class, () -> {
            topicValidatorV1.validateCreate(topicE);
        });
        assertEquals("Топик не должен быть пустым или содержать только пробелы", exception.getMessage());
    }

    @Test
    public void testValidateCreateTitleTooShort() {
        TopicE topicE = new TopicE();
        topicE.setTitle("ab");
        List<String> errors = topicValidatorV1.validateCreate(topicE);
        assertFalse(errors.isEmpty());
        assertTrue(errors.contains("Топик не должен содержать меньше 3-х символов или превышать 100"));
    }

    @Test
    public void testValidateCreateTitleTooLong() {
        TopicE topicE = new TopicE();
        String longTitle = "a".repeat(101);
        topicE.setTitle(longTitle);
        List<String> errors = topicValidatorV1.validateCreate(topicE);
        assertFalse(errors.isEmpty());
        assertTrue(errors.contains("Топик не должен содержать меньше 3-х символов или превышать 100"));
    }

    //Тесты для validateFindById

    @Test
    public void testValidateFindByIdValidId() {
        List<String> errors = topicValidatorV1.validateFindById(10L);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateFindByIdNullId() {
        List<String> errors = topicValidatorV1.validateFindById(null);
        assertEquals(1, errors.size());
        assertEquals("Идентификатор топика не может быть null", errors.get(0));
    }

    @Test
    public void testValidateFindByIdNegativeId() {
        List<String> errors = topicValidatorV1.validateFindById(-5L);
        assertEquals(1, errors.size());
        assertEquals("Идентификатор топика должен быть положительным", errors.get(0));
    }

    //Тесты для validateUpdateById

    @Test
    public void testValidateUpdateByIdValid() {
        TopicE topicE = new TopicE();
        topicE.setTitle("Valid Title");

        List<String> errors = topicValidatorV1.validateUpdateById(5L, topicE);

        assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateUpdateByIdNullTopic() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            topicValidatorV1.validateUpdateById(5L, null);
        });

        assertEquals("Идентификатор топика не может быть null", exception.getMessage());
    }

    @Test
    public void testValidateUpdateByIdNegativeId() {
        TopicE topicE = new TopicE();
        topicE.setTitle("Valid");

        List<String> errors = topicValidatorV1.validateUpdateById(-1L, topicE);

        assertTrue(errors.contains("Идентификатор топика должен быть положительным"));

    }

    @Test
    public void testValidateUpdateByIdTitleNull() {

        TopicE topicE = new TopicE();
        topicE.setTitle(null);
        topicE.setId(1L);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            topicValidatorV1.validateUpdateById(topicE.getId(), topicE);
        });

        // Проверяем сообщение исключения
        assertEquals("Заголовок топика не должен быть пустым", exception.getMessage());

}


@Test
    public void testValidateUpdateByIdTitleTooShortOrLong() {
        TopicE validTopicE = new TopicE();
        validTopicE.setTitle("ab");
        List<String> errorsShort = topicValidatorV1.validateUpdateById(1L, validTopicE);
        assertTrue(errorsShort.contains("Топик не должен содержать меньше 3-х символов или превышать 100"));

        String longTitle = "a".repeat(101);
        validTopicE.setTitle(longTitle);
        List<String> errorsLong= topicValidatorV1.validateUpdateById(1L, validTopicE);
        assertTrue(errorsLong.contains("Топик не должен содержать меньше 3-х символов или превышать 100"));
    }

    //Тесты для validateDeleteById

    @Test
    public void testValidateDeleteByIdValid() {
        List<String> errors= topicValidatorV1.validateDeleteById(10L);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void testValidateDeleteByIdNull() {
        List<String> errors= topicValidatorV1.validateDeleteById(null);
        assertEquals(1,errors.size());
        assertEquals("Идентификатор топика не может быть null",errors.get(0));
    }

    @Test
    public void testValidateDeleteByIdNegativeOrZero() {
        List<String> errorsNeg= topicValidatorV1.validateDeleteById(-5L);
        List<String> errorsZero= topicValidatorV1.validateDeleteById(0L);

        assertTrue(errorsNeg.contains("Идентификатор топика должен быть положительным"));
        assertTrue(errorsZero.contains("Идентификатор топика должен быть положительным"));
    }
    /*

    Возможно, вы ожидали, что при null ID не должно выбрасываться исключение, но ваш валидатор это делает — это нормально,
     потому что ID не должен быть null для удаления.
    Или, наоборот, вы хотите проверить, что при null ID выбрасывается именно это исключение — тогда тест написан правильно.

    Ваша ошибка — это не ошибка, а ожидаемое поведение: при null ID выбрасывается ValidationException
    с сообщением "Идентификатор топика не может быть null".

    Если вы хотите проверить этот сценарий, то тест прошел успешно.
    Если вы хотите изменить поведение, нужно изменить реализацию метода validateDeleteById.
        */
}