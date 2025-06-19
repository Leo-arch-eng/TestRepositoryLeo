package com.example.leo_forum_project_test.mapper;

import com.example.leo_forum_project_test.dto.CommentDto;
import com.example.leo_forum_project_test.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Mapping(source = "localDateTime", target = "date", qualifiedByName = "localDateTimeToString")
    CommentDto toDto(Comment comment);

    @Mapping(source = "date", target = "localDateTime", qualifiedByName = "stringToLocalDateTime")
    Comment toEntity(CommentDto commentDto);

    @Named("localDateTimeToString")
    static String localDateTimeToString(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return localDateTime.format(FORMATTER);
    }

    @Named("stringToLocalDateTime")
    static LocalDateTime stringToLocalDateTime(String date) {
        if (date == null || date.isEmpty()) return null;
        return LocalDateTime.parse(date, FORMATTER);
    }
}