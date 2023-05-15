package com.bogdan.onlinelibrary.repository;

import com.bogdan.onlinelibrary.entity.Book;
import com.bogdan.onlinelibrary.repository.generic.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends GenericRepository<Book> {
}
