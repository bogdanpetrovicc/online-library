package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.entity.Author;
import com.bogdan.onlinelibrary.entity.Book;
import com.bogdan.onlinelibrary.entity.domain.Genre;
import com.bogdan.onlinelibrary.service.AuthorService;
import com.bogdan.onlinelibrary.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;

    // PAGES START
    @GetMapping("/add")
    public String getAddBookPage(Model model) {
        model.addAllAttributes(Map.of(
                "book", new Book(),
                "authors", authorService.findAll(),
                "genres", Genre.values()
        ));
        return "book/save-book";
    }

    @GetMapping("/update")
    public String getUpdateBookPage(@RequestParam(value = "bookId", required = false) Integer bookId, Model model) {
        Book book = bookId != null ? bookService.findById(bookId) : new Book();

        model.addAllAttributes(Map.of(
                "book", book,
                "authors", authorService.findAll(),
                "genres", Genre.values()
        ));

        return "book/update-book";
    }

    @GetMapping("")
    public String getAvailableBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "book/books";
    }
    // PAGES END

    @PostMapping("")
    public String saveBook(@Valid @ModelAttribute("book") Book book,
                           @RequestParam("authorId") Integer authorId,
                           BindingResult result) {
        if (result.hasErrors()) {
            throw new RuntimeException("Book data are invalid!");
        }

        Author author = authorService.findById(authorId);
        book.setAuthor(author);

        bookService.save(book);
        return "redirect:/books";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam("id") Integer id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PostMapping("/update")
    public String updateBook(@Valid @ModelAttribute("book") Book book,
                             @RequestParam("bookId") Integer bookId,
                             @RequestParam("authorId") Integer authorId,
                             BindingResult result) {
        if (result.hasErrors()) {
            throw new RuntimeException("Book data are invalid!");
        }
        bookService.updateBook(book, bookId, authorId);
        return "redirect:/books/";
    }
}
