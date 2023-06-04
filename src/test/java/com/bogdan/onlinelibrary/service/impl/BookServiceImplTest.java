package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.Author;
import com.bogdan.onlinelibrary.entity.Book;
import com.bogdan.onlinelibrary.entity.domain.Genre;
import com.bogdan.onlinelibrary.repository.BookRepository;
import com.bogdan.onlinelibrary.service.AuthorService;
import com.bogdan.onlinelibrary.service.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {BookServiceImpl.class})
public class BookServiceImplTest {
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorService authorService;
    @Autowired
    private BookService bookService;

    private Author author;
    private Book book;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Test data
        author = new Author();
        author.setId(1);
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setAge(35);
        author.setCountry("USA");

        book = new Book();
        book.setId(1);
        book.setAuthor(author);
        book.setName("Test Book");
        book.setPrice(10.99);
        book.setImage("book_image.jpg");
        book.setGenre(Genre.BLOCKCHAIN);
        book.setAmount(10);
        book.setDescription("This is a test book.");
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testUpdateBook() {
        Integer bookId = 1;
        Integer authorId = 1;

        when(bookRepository.save(book)).thenReturn(book);
        when(authorService.findById(authorId)).thenReturn(author);

        book.setName("Test");
        Book updatedBook = bookService.updateBook(book, bookId, authorId);

        assertEquals(authorId, updatedBook.getAuthor().getId());
        assertEquals(bookId, updatedBook.getId());
        assertEquals("Test", updatedBook.getName());
    }
}