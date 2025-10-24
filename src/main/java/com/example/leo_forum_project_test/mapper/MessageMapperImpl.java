package com.example.leo_forum_project_test.mapper;

import com.example.leo_forum_project_test.dto.MessageDto;
import com.example.leo_forum_project_test.entity.MessageE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Slf4j
@Service
public class MessageMapperImpl implements MessageMapper{


    @Override
    public MessageDto toDto(MessageE messageE) {
        return new MessageDto(
                messageE.getMessageId(),
                messageE.getAuthorName(),
                messageE.getAuthorSurname(),
                messageE.getMessage(),
                messageE.getLocalDateTime().toString(),
                messageE.getTopicId()
        );
    }

    @Override
    public MessageE toEntity(MessageDto messageDto) {
        log.info("toEntity");
        LocalDateTime localDateTime = null;
        String dateStr = messageDto.getDate();

        if (dateStr != null && !dateStr.isBlank()) {
            try {
                localDateTime = LocalDateTime.parse(dateStr);
            } catch (DateTimeParseException e) {
                log.error(
                        "Не удалось преобразовать тип 'String' в тип 'LocalDateTime'. " +
                                "Доп информация об ошибке: %s".formatted(e.getLocalizedMessage()),
                        e
                );
                throw new RuntimeException(e);
            }
        }

        return new MessageE(
                null,
                messageDto.getAuthorName(),
                messageDto.getAuthorSurname(),
                messageDto.getMessage(),
                localDateTime,
                messageDto.getTopicId()

        );
    }
}
