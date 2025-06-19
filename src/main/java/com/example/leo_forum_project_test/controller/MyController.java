package com.example.leo_forum_project_test.controller;


import com.example.leo_forum_project_test.entity.Author;
import com.example.leo_forum_project_test.entity.Message;
import com.example.leo_forum_project_test.service.AuthorService;
import com.example.leo_forum_project_test.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/forum")
public class MyController {

    @Autowired
    private AuthorService authorService; // создаем переменную с вызовом интерфейса сервиса

    @GetMapping("/allAuthors")
    public String showAllAuthors(Model model) {

        List<Author> allAuthors = authorService.getAllAuthors(); // помещаем список авторов в лист
        // все авторы на форуме, кто писал для моего видения

        model.addAttribute("allAuthors", allAuthors);


        return "all-authors";
    }


    private void addAuthor(Author author) {

    }




    @Autowired
    private MessageService messageService;

    @GetMapping
    public String showAllMessages(Model model) {

        List<Message> allMessages = messageService.getAllMessages();


    }



}
