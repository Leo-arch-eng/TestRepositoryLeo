package com.example.leo_forum_project_test.service.mock;

import com.example.leo_forum_project_test.dto.Author;
import com.example.leo_forum_project_test.repository.AuthorRepositoryV1;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorServiceV1MockTest {

    @Mock
    private AuthorRepositoryV1 authorRepositoryV1;

    @InjectMocks
    private AuthorServiceV1Mock authorService;

    private Validator validator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        authorService = new AuthorServiceV1Mock();
    }

    // Вспомогательный метод для валидации DTO
    private Set<ConstraintViolation<Author>> validateAuthor(Author author) {
        return validator.validate(author);
    }

    // 1. Тест создания автора с валидными данными
    @Test
    public void testCreateAuthorValid() {
        Author author = new Author(null, "Иван", "Иванов", "test@example.com", 30);
        when(authorRepositoryV1.createAuthor(any(Author.class))).thenReturn(author);

        Author result = authorService.createAuthor(author);

        verify(authorRepositoryV1, times(1)).createAuthor(author);
        assertEquals(author, result);
    }

    // 2. Тест создания автора с некорректными данными (например, пустое имя)
    @Test
    public void testCreateAuthorInvalidData() {
        Author author = new Author(null, "", "Иванов", "test@example.com", 30);
        Set<ConstraintViolation<Author>> violations = validateAuthor(author);
        assertFalse(violations.isEmpty());
    }

    // 3. Тест обновления автора с существующим ID
    @Test
    public void testUpdateAuthorSuccess() {
        Long authorId = 1L;
        Author existingAuthor = new Author(authorId, "Иван", "Иванов", "test@example.com", 30);
        Author updatedAuthor = new Author(authorId, "Петр", "Петров", "petr@example.com", 40);

        when(authorRepositoryV1.updateAuthor(eq(authorId), any(Author.class))).thenReturn(updatedAuthor);

        Author result = authorService.updateAuthor(authorId, updatedAuthor);

        verify(authorRepositoryV1, times(1)).updateAuthor(eq(authorId), any(Author.class));
        assertEquals(updatedAuthor, result);
    }

    // 4. Тест обновления несуществующего автора (возвращается null)
    @Test
    public void testUpdateAuthorNotFound() {
        Long authorId = 999L;
        Author newData = new Author(null, "Петр", "Петров", "petr@example.com", 40);

        when(authorRepositoryV1.updateAuthor(eq(authorId), any(Author.class))).thenReturn(null);

        Author result = authorService.updateAuthor(authorId, newData);

        verify(authorRepositoryV1, times(1)).updateAuthor(eq(authorId), any(Author.class));
        assertNull(result);
    }

    // 5. Тест удаления автора успешно
    @Test
    public void testDeleteAuthorSuccess() {
        Long authorId = 1L;
        doNothing().when(authorRepositoryV1).deleteAuthor(authorId);

        assertDoesNotThrow(() -> authorService.deleteAuthor(authorId));
        verify(authorRepositoryV1, times(1)).deleteAuthor(authorId);
    }

    // 6. Тест удаления автора с исключением
    @Test
    public void testDeleteAuthorException() {
        Long authorId = 1L;
        doThrow(new RuntimeException("Ошибка удаления")).when(authorRepositoryV1).deleteAuthor(authorId);

        // Проверка, что исключение не пробрасывается, а логируется
        assertDoesNotThrow(() -> authorService.deleteAuthor(authorId));
        verify(authorRepositoryV1, times(1)).deleteAuthor(authorId);
    }

    // 7. Тест поиска автора по ID
    @Test
    public void testFindAuthorByIdFound() {
        Long authorId = 1L;
        Author author = new Author(authorId, "Иван", "Иванов", "test@example.com", 30);
        when(authorRepositoryV1.findAuthorById(authorId)).thenReturn(author);

        Author result = authorService.findAuthorById(authorId);

        verify(authorRepositoryV1, times(1)).findAuthorById(authorId);
        assertEquals(author, result);
    }

    // 8. Тест поиска автора по ID, когда не найден
    @Test
    public void testFindAuthorByIdNotFound() {
        Long authorId = 999L;
        when(authorRepositoryV1.findAuthorById(authorId)).thenReturn(null);

        Author result = authorService.findAuthorById(authorId);

        verify(authorRepositoryV1).findAuthorById(authorId);
        assertNull(result);
    }

    // 9. Тест валидации при вызове createAuthor (имитация Spring)
    @Test
    public void testCreateAuthorValidationFails() {
        Author invalidAuthor = new Author(null, "", "Иванов", "bademail", -5);
        Set<ConstraintViolation<Author>> violations = validateAuthor(invalidAuthor);
        assertFalse(violations.isEmpty());
        // Можно дополнительно проверить, что валидация обнаружила ошибки
    }

    // 10. Тест, что при исключении в репозитории логируется ошибка (можно проверить логирование, если настроить)
    // (Это более сложный сценарий, обычно для логирования используют специальные библиотеки или тестовые фреймворки)

}