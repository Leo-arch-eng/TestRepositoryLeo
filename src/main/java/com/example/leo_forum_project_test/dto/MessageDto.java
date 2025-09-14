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
public class MessageDto {

    private Long id;

    @NotBlank(message = "Автор не может быть пустым")
    @Size(min = 1, max = 50, message = "Имя автора должно содержать от {min} до {max} символов")
    private String authorName;

    @NotBlank(message = "Фамилия автора не может быть пустая")
    @Size(min = 1, max = 50, message = "Фамилия автора должно содержать от {min} до {max} символов")
    private String authorSurname;

    @NotBlank(message = "Сообщение не может быть пустым")
    @Size(min = 1, max = 500, message = "Сообщение должно содержать от {min} до {max} символов")
    private String message;

    private String date;

}

