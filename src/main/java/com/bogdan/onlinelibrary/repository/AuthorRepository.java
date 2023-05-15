package com.bogdan.onlinelibrary.repository;

import com.bogdan.onlinelibrary.entity.Author;
import com.bogdan.onlinelibrary.repository.generic.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends GenericRepository<Author> {
}
