package com.bogdan.onlinelibrary.repository;

import com.bogdan.onlinelibrary.entity.Book;
import com.bogdan.onlinelibrary.repository.generic.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends GenericRepository<Book> {
}
