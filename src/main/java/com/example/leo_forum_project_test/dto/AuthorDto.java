package com.example.leo_forum_project_test.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {

    private Long authorId;

    @NotBlank(message = "Имя автора не может быть пустым")
    @Size(min = 1, max = 50, message = "Имя автора должно содержать от {min} до {max} символов")
    private String name;

    @NotBlank(message = "Фамилия автора не может быть пустая")
    @Size(min = 1, max = 50, message = "Фамилия автора должно содержать от {min} до {max} символов")
    private String surname;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;

    @Min(value = 0, message = "Возраст не может быть отрицательным")
    @Max(value = 150, message = "Возраст слишком большой")
    private int age;

}
