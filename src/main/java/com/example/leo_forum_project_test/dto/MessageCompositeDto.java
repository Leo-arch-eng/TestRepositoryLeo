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
public class MessageCompositeDto {
    private Long id;
    private String authorName;
    private String authorSurname;
    private String message;
    private String date;
    private Long topicId;
    private List<CommentDto> commentOfMessage;

}
