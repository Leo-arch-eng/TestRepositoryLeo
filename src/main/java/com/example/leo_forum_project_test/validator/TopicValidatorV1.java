package com.example.leo_forum_project_test.validator;

import com.example.leo_forum_project_test.entity.TopicE;

import java.util.List;

public interface TopicValidatorV1 {
    List<String> validateCreate(TopicE topicE);

    List<String> validateFindById(Long topicId);

    List<String> validateUpdateById(Long topicId, TopicE topicE);

    List<String> validateDeleteById(Long topicId);
}
