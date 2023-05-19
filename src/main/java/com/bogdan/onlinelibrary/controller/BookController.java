package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("")
    public String getBooksPage(Model model) {
        model.addAttribute("books", bookService.findAllAvailableBooks());
        return "book/books";
    }
}
