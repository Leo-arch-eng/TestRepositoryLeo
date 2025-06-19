package com.example.leo_forum_project_test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;

    @NotBlank(message = "Имя автора не может быть пустым")
    @Size(min = 1, max = 50, message = "Имя автора должно содержать от {min} до {max} символов")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Фамилия автора не может быть пустая")
    @Size(min = 1, max = 50, message = "Фамилия автора должно содержать от {min} до {max} символов")
    @Column(name = "surname")
    private String surname;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат email")
    @Column(name = "email")
    private String email;

    @Min(value = 0, message = "Возраст не может быть отрицательным")
    @Max(value = 150, message = "Возраст слишком большой")
    @Column(name = "age")
    private int age;

    public Author(Long authorId, String name, String surname, String email, int age) {
        this.authorId = authorId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
    }

    public Author() {
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
