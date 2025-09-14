package com.example.leo_forum_project_test.service.mock;

import com.example.leo_forum_project_test.entity.AuthorE;
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

public class AuthorEDtoServiceV1MockTest {

    @Mock
    private AuthorRepositoryV1 authorRepositoryV1;

    @InjectMocks
    private AuthorServiceV1Mock authorService;

    private Validator validator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = Validation.buildDefaultValidatorFactory().getValidator();

    }

    // Вспомогательный метод для валидации DTO
    private Set<ConstraintViolation<AuthorE>> validateAuthor(AuthorE authorE) {
        return validator.validate(authorE);
    }

    // 1. Тест создания автора с валидными данными
    @Test
    public void testCreateAuthorValid() {
        AuthorE authorE = new AuthorE(null, "Иван", "Иванов", "test@example.com", 30);
        when(authorRepositoryV1.createAuthor(any(AuthorE.class))).thenReturn(authorE);

        AuthorE result = authorService.createAuthor(authorE);

        verify(authorRepositoryV1, times(1)).createAuthor(authorE);
        assertEquals(authorE, result);
    }

    // 2. Тест создания автора с некорректными данными (например, пустое имя)
    @Test
    public void testCreateAuthorInvalidData() {
        AuthorE authorE = new AuthorE(null, "", "Иванов", "test@example.com", 30);
        Set<ConstraintViolation<AuthorE>> violations = validateAuthor(authorE);
        assertFalse(violations.isEmpty());
    }

    // 3. Тест обновления автора с существующим ID
    @Test
    public void testUpdateAuthorSuccess() {
        Long authorId = 1L;
        AuthorE existingAuthorE = new AuthorE(authorId, "Иван", "Иванов", "test@example.com", 30);
        AuthorE updatedAuthorE = new AuthorE(authorId, "Петр", "Петров", "petr@example.com", 40);

        when(authorRepositoryV1.updateAuthor(eq(authorId), any(AuthorE.class))).thenReturn(updatedAuthorE);

        AuthorE result = authorService.updateAuthor(authorId, updatedAuthorE);

        verify(authorRepositoryV1, times(1)).updateAuthor(eq(authorId), any(AuthorE.class));
        assertEquals(updatedAuthorE, result);
    }

    // 4. Тест обновления несуществующего автора (возвращается null)
    @Test
    public void testUpdateAuthorNotFound() {
        Long authorId = 999L;
        AuthorE newData = new AuthorE(null, "Петр", "Петров", "petr@example.com", 40);

        when(authorRepositoryV1.updateAuthor(eq(authorId), any(AuthorE.class))).thenReturn(null);

        AuthorE result = authorService.updateAuthor(authorId, newData);

        verify(authorRepositoryV1, times(1)).updateAuthor(eq(authorId), any(AuthorE.class));
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
        AuthorE authorE = new AuthorE(authorId, "Иван", "Иванов", "test@example.com", 30);
        when(authorRepositoryV1.findAuthorById(authorId)).thenReturn(authorE);

        AuthorE result = authorService.findAuthorById(authorId);

        verify(authorRepositoryV1, times(1)).findAuthorById(authorId);
        assertEquals(authorE, result);
    }

    // 8. Тест поиска автора по ID, когда не найден
    @Test
    public void testFindAuthorByIdNotFound() {
        Long authorId = 999L;
        when(authorRepositoryV1.findAuthorById(authorId)).thenReturn(null);

        AuthorE result = authorService.findAuthorById(authorId);

        verify(authorRepositoryV1).findAuthorById(authorId);
        assertNull(result);
    }

    // 9. Тест валидации при вызове createAuthor
    @Test
    public void testCreateAuthorValidationFails() {
        AuthorE invalidAuthorE = new AuthorE(null, "", "Иванов", "bademail", -5);
        Set<ConstraintViolation<AuthorE>> violations = validateAuthor(invalidAuthorE);
        assertFalse(violations.isEmpty());

    }

}