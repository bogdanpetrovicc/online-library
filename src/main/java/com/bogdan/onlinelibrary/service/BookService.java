package com.bogdan.onlinelibrary.service;

import com.bogdan.onlinelibrary.entity.Book;
import com.bogdan.onlinelibrary.service.generic.GenericService;


public interface BookService extends GenericService<Book> {

    void updateBook(Book book, Integer bookId, Integer authorId);
}
