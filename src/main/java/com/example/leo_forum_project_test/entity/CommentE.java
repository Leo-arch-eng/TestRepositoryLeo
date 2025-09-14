package com.example.leo_forum_project_test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@Table(name = "comment")
public class CommentE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="commentid")
    private Long id;

    @NotBlank(message = "Комментарий не может быть пустым")
    @Size(min = 1, max = 300, message = "Комментарий должен содержать от {min} до {max} символов")
    @Column(name = "comment")
    private String comment;

    @NotBlank(message = "Автор не может быть пустым")
    @Size(min = 1, max = 50, message = "Имя автора должно содержать от {min} до {max} символов")
    @Column(name = "author")
    private String author;

    @CreationTimestamp
    @Column(name = "date", nullable = false, updatable = false)
    private LocalDateTime localDateTime;


    public CommentE() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getDate() {
        return localDateTime;
    }

    public void setDate(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long commentId) {
        this.id = commentId;
    }
}



