package com.example.leo_forum_project_test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopicCompositeDto {
    private Long id;
    private String title;
    private String description;
    private List<MessageDto> messageOfTopic;
}