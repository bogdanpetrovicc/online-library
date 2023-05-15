package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.Author;
import com.bogdan.onlinelibrary.repository.AuthorRepository;
import com.bogdan.onlinelibrary.service.AuthorService;
import com.bogdan.onlinelibrary.service.generic.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl extends GenericServiceImpl<Author> implements AuthorService {

    protected AuthorServiceImpl(AuthorRepository authorRepository) {
        super(authorRepository);
    }
}
