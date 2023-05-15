package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.Book;
import com.bogdan.onlinelibrary.repository.BookRepository;
import com.bogdan.onlinelibrary.service.BookService;
import com.bogdan.onlinelibrary.service.generic.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl extends GenericServiceImpl<Book> implements BookService {

    protected BookServiceImpl(BookRepository bookRepository) {
        super(bookRepository);
    }
}
