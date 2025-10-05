package com.example.leo_forum_project_test.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
@Entity
@Table(name = "message")
public class MessageE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;

    @NotBlank(message = "Автор не может быть пустым")
    @Size(min = 1, max = 50, message = "Имя автора должно содержать от {min} до {max} символов")
    @Column(name="author_name")
    private String authorName;

    @NotBlank(message = "Фамилия автора не может быть пустая")
    @Size(min = 1, max = 50, message = "Фамилия автора должно содержать от {min} до {max} символов")
    @Column(name="author_surname")
    private String authorSurname;

    @NotBlank(message = "Сообщение не может быть пустым")
    @Size(min = 1, max = 500, message = "Сообщение должно содержать от {min} до {max} символов")
    @Column(name = "message")
    private String message;


    @CreationTimestamp
    @Column(name = "local_Date", updatable = false)
    @JsonProperty("localDate")
    private LocalDateTime localDateTime;

    public MessageE(Long messageId,
                    String authorName,
                    String authorSurname,
                    String message,
                    LocalDateTime localDateTime
    ) {
        this.messageId = messageId;
        this.authorName = authorName;
        this.authorSurname = authorSurname;
        this.message = message;
        this.localDateTime = localDateTime;
    }

    public MessageE() {
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

}

