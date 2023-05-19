package com.bogdan.onlinelibrary.service;

import com.bogdan.onlinelibrary.entity.Book;
import com.bogdan.onlinelibrary.service.generic.GenericService;

import java.util.List;

public interface BookService extends GenericService<Book> {
    List<Book> findAllAvailableBooks();
}
