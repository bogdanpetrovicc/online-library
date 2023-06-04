package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.entity.Author;
import com.bogdan.onlinelibrary.entity.Book;
import com.bogdan.onlinelibrary.entity.domain.Genre;
import com.bogdan.onlinelibrary.service.AuthorService;
import com.bogdan.onlinelibrary.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {BookController.class})
public class BookControllerTest {
    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private Model model;
    @MockBean
    private BindingResult bindingResult;

    @Autowired
    private BookController bookController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAddBookPage() {
        Author author1 = new Author(1, "John", "Doe", 30, "Canada");
        Author author2 = new Author(2, "Mike", "Smith", 40, "USA");
        List<Author> authors = Arrays.asList(author1, author2);

        when(authorService.findAll()).thenReturn(authors);

        String result = bookController.getAddBookPage(model);

        assertEquals("book/save-book", result);
    }

    @Test
    public void testGetUpdateBookPage_WithExistingBook() {
        int bookId = 1;
        Book book = new Book();
        book.setId(bookId);
        Author author = new Author();
        List<Author> authors = List.of(author);

        when(bookService.findById(bookId)).thenReturn(book);
        when(authorService.findAll()).thenReturn(authors);

        String result = bookController.getUpdateBookPage(bookId, model);

        assertEquals("book/update-book", result);
    }

    @Test
    public void testGetUpdateBookPage_WithoutExistingBook() {
        Author author = new Author();
        List<Author> authors = List.of(author);

        when(authorService.findAll()).thenReturn(authors);

        String result = bookController.getUpdateBookPage(null, model);

        assertEquals("book/update-book", result);
    }

    @Test
    public void testGetAvailableBooks() {
        List<Book> books = Arrays.asList(new Book(), new Book());
        when(bookService.findAll()).thenReturn(books);

        String result = bookController.getAvailableBooks(model);

        assertEquals("book/books", result);
        verify(model, times(1)).addAttribute("books", books);
    }

    @Test
    public void testSaveBook_WithValidData() {
        int authorId = 1;
        Book book = new Book();
        Author author = new Author();
        book.setAuthor(author);

        String result = bookController.saveBook(book, authorId, bindingResult);

        assertEquals("redirect:/books", result);
        verify(bookService, times(1)).save(book);
        verify(authorService, times(1)).findById(authorId);
    }

    @Test
    public void testSaveBook_WithInvalidData() {
        int authorId = 1;
        Book book = new Book();
        when(bindingResult.hasErrors()).thenReturn(true);

        assertThrows(RuntimeException.class, () -> bookController.saveBook(book, authorId, bindingResult));
        verify(bookService, never()).save(book);
        verify(authorService, never()).findById(authorId);
    }

    @Test
    public void testDeleteBook() {
        int bookId = 1;

        String result = bookController.deleteBook(bookId);

        assertEquals("redirect:/books", result);
        verify(bookService, times(1)).delete(bookId);
    }

    @Test
    public void testUpdateBook_WithValidData() {
        int bookId = 1;
        int authorId = 2;
        Book book = new Book();
        Author author = new Author();
        book.setAuthor(author);

        String result = bookController.updateBook(book, bookId, authorId, bindingResult);

        assertEquals("redirect:/books", result);
        verify(bookService, times(1)).updateBook(book, bookId, authorId);
    }

    @Test
    public void testUpdateBook_WithInvalidData() {
        int bookId = 1;
        int authorId = 2;
        Book book = new Book();
        when(bindingResult.hasErrors()).thenReturn(true);

        assertThrows(RuntimeException.class, () -> bookController.updateBook(book, bookId, authorId, bindingResult));
        verify(bookService, never()).updateBook(book, bookId, authorId);
    }
}
