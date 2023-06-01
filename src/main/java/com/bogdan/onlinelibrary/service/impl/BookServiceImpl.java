package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.Author;
import com.bogdan.onlinelibrary.entity.Book;
import com.bogdan.onlinelibrary.repository.BookRepository;
import com.bogdan.onlinelibrary.service.AuthorService;
import com.bogdan.onlinelibrary.service.BookService;
import com.bogdan.onlinelibrary.service.generic.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class BookServiceImpl extends GenericServiceImpl<Book> implements BookService {

    private final AuthorService authorService;

    protected BookServiceImpl(BookRepository bookRepository, AuthorService authorService) {
        super(bookRepository);
        this.authorService = authorService;
    }

    @Override
    public void updateBook(Book book, Integer bookId, Integer authorId) {
        Author author = authorService.findById(authorId);
        book.setAuthor(author);
        book.setId(bookId);
        genericRepository.save(book);
    }
}
