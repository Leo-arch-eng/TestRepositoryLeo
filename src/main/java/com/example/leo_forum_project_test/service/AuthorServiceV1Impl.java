package com.example.leo_forum_project_test.service;

import com.example.leo_forum_project_test.dto.AuthorDto;
import com.example.leo_forum_project_test.entity.AuthorE;
import com.example.leo_forum_project_test.mapper.AuthorMapper;
import com.example.leo_forum_project_test.repository.AuthorRepositoryV2;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RequiredArgsConstructor
@Service
public class AuthorServiceV1Impl implements AuthorServiceV1 {

    private final AuthorRepositoryV2 authorRepositoryV2;
    private final AuthorMapper authorMapper;

    @Override
    public AuthorDto createAuthor(@Valid AuthorDto authorDto) {
        log.info("Создание нового автора: {}", authorDto);
        AuthorE authorEEntity = authorMapper.toEntity(authorDto);
        AuthorE savedAuthorE = authorRepositoryV2.save(authorEEntity);
        return authorMapper.toDto(savedAuthorE);
    }

    @Override
    public AuthorDto updateAuthor(Long authorId, @Valid AuthorDto authorDto) {
        log.info("Обновление автора по ID: {}", authorId);
        Optional<AuthorE> optionalAuthor = authorRepositoryV2.findById(authorId);
        if (optionalAuthor.isPresent()) {
            AuthorE existingAuthorE = optionalAuthor.get();
            // Обновляем поля
            existingAuthorE.setName(authorDto.getName());
            existingAuthorE.setSurname(authorDto.getSurname());
            existingAuthorE.setEmail(authorDto.getEmail());
            existingAuthorE.setAge(authorDto.getAge());
            AuthorE updatedAuthorE = authorRepositoryV2.save(existingAuthorE);
            return authorMapper.toDto(updatedAuthorE);
        } else {
            log.warn("Автор с ID: {} не найден", authorId);
            return null;
        }
    }

    @Override
    public void deleteAuthor(Long authorId) {
        log.info("Удаление автора по ID: {}", authorId);
        try {
            authorRepositoryV2.deleteById(authorId);
        } catch (Exception e) {
            log.error("Ошибка при удалении автора: {}", e.getMessage());
        }
    }

    @Override
    public AuthorDto findAuthorById(Long authorId) {
        log.info("Поиск автора по ID: {}", authorId);
        Optional<AuthorE> optionalAuthor = authorRepositoryV2.findById(authorId);
        return optionalAuthor.map(authorMapper::toDto).orElse(null);
    }

    @Override
    public List<AuthorDto> findAllAuthors() {
        log.info("Начинается поиск всех авторов");
        List<AuthorE> authors = authorRepositoryV2.findAll();
        if(authors.isEmpty()){
            log.warn("Запрашиваемый список авторов " + authors + "пуст");
            throw new ValidationException("Ошибка валидации,список не найден");
        }
        log.info("Список авторов успешно найден");
        return authors.stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<AuthorDto> findAllAuthorsPaginated(
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        if (!isFieldSortable(sortBy)) {
            throw new ValidationException("Недопустимое поле сортировки: " + sortBy);
        }

        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortDir)) {
            direction = Sort.Direction.DESC;
        } else if (!"asc".equalsIgnoreCase(sortDir)) {
            throw new ValidationException("Недопустимое направление сортировки: " + sortDir);
        }

        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AuthorE> authorPage = authorRepositoryV2.findAll(pageable);

        if (authorPage.isEmpty()) {
            log.warn("Список авторов пуст");
            throw new ValidationException("Авторы не найдены");
        }

        log.info("Обнаружено {} авторов", authorPage.getTotalElements());

        return authorPage.getContent()
                .stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }

    // Вспомогательный метод проверки сортируемых полей для автора
    private boolean isFieldSortable(String fieldName) {
        return "id".equals(fieldName) ||
                "name".equals(fieldName) ||
                "surname".equals(fieldName) ||
                "email".equals(fieldName) ||
                "age".equals(fieldName);
    }
}