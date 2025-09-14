package com.example.leo_forum_project_test.validator;

import com.example.leo_forum_project_test.entity.TopicE;
import com.example.leo_forum_project_test.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicValidatorV1Impl implements TopicValidatorV1 {

    public List<String> validateCreate(TopicE topicE) {
        List<String> errors = new ArrayList<>();

        checkIfObjectNullThrow(topicE, "Топик не должен быть пустым");

        String title = topicE.getTitle();
        checkIfStringNullThrow(title, "Топик не должен быть пустым или содержать только пробелы");
        if (title.length() > 100 || title.length() < 3) {
            errors.add("Топик не должен содержать меньше 3-х символов или превышать 100");
        }
        return errors;
    }

    public List<String> validateFindById(Long topicId) {
        List<String> errors = new ArrayList<>();

        if (topicId == null) {
            errors.add("Идентификатор топика не может быть null");
        } else if (topicId <= 0) {
            errors.add("Идентификатор топика должен быть положительным");
        }
        return errors;
    }

    public List<String> validateUpdateById(Long topicId, TopicE topicE) {
        List<String> errors = new ArrayList<>();

        checkIfObjectNullThrow(topicE, "Идентификатор топика не может быть null");
        if (topicId <= 0) {
            errors.add("Идентификатор топика должен быть положительным");
        }
        checkIfObjectNullThrow(topicE, "Топик не должен быть пустым");
        String title = topicE.getTitle();
        checkIfStringNullThrow(title, "Заголовок топика не должен быть пустым");
        if (title.length() > 100 || title.length() < 3) {
            errors.add("Топик не должен содержать меньше 3-х символов или превышать 100");
        }
        return errors;
    }

    public List<String> validateDeleteById(Long topicId) {
        List<String> errors = new ArrayList<>();
        checkIfObjectNullThrow(topicId, "Идентификатор топика не может быть null");
        if (topicId <= 0) {
            errors.add("Идентификатор топика должен быть положительным");
        }
        return errors;
    }

    private void checkIfStringNullThrow(String str, String errorMessage) {
        checkIfObjectNullThrow(str, errorMessage);
        String trimmedString = str.trim();
        if (trimmedString.isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }

    private void checkIfObjectNullThrow(Object object, String errorMessage) {
        if (object == null) {
            throw new ValidationException(errorMessage);
        }
    }

}







