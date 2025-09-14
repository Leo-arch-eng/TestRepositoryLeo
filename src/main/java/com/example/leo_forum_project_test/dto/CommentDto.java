package com.example.leo_forum_project_test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;

    @NotBlank(message = "Комментарий не может быть пустым")
    @Size(min = 1, max = 300, message = "Комментарий должен содержать от {min} до {max} символов")
    private String comment;

    @NotBlank(message = "Автор не может быть пустым")
    @Size(min = 1, max = 50, message = "Имя автора должно содержать от {min} до {max} символов")
    private String author;

    private String date;
}



