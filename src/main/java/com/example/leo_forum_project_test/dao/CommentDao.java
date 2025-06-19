package com.example.leo_forum_project_test.dao;

import com.example.leo_forum_project_test.entity.Comment;

import java.util.List;

public interface CommentDao {

    public void insert(Comment comment);

    public void update(Comment comment);

    public void delete(Comment comment);

    public Comment findById(int id);

    public List<Comment> findAll();
}
