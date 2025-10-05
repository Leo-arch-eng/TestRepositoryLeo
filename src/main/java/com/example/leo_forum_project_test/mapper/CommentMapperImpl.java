package com.example.leo_forum_project_test.mapper;

import com.example.leo_forum_project_test.dto.CommentDto;
import com.example.leo_forum_project_test.entity.CommentE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Slf4j
@Component
public class CommentMapperImpl implements CommentMapper{
    @Override
    public CommentDto toDto(CommentE commentE) {
        return new CommentDto(
                commentE.getId(),
                commentE.getComment(),
                commentE.getAuthor(),
                commentE.getDate().toString());
    }

    @Override
    public CommentE toEntity(CommentDto commentDto) {
        log.info("toEntity");
        LocalDateTime localDateTime = null;
        String dateString = commentDto.getDate();

        if (dateString != null && dateString.isBlank()) {
            try {
                localDateTime = LocalDateTime.parse(dateString);
            } catch (DateTimeParseException e) {
                log.error("Не удалось преобразовать тип 'String' в тип 'LocalDateTime'. \" +\n" +
                        "                    \"Доп информация об ошибке: %s \".formatted(e.getLocalizedMessage()),\n" +
                        "                    e"
                );
                throw new RuntimeException(e);
            }
        }

        return new CommentE(
                null,
                commentDto.getComment(),
                commentDto.getAuthor(),
                localDateTime
                );
    }
}
