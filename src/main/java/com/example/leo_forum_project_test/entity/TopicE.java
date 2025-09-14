package com.example.leo_forum_project_test.entity;

import jakarta.persistence.*;
import lombok.Builder;

@Builder
@Entity
@Table(name = "topic")
public class TopicE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name="description")
    private String description;

    public TopicE(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public TopicE() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
