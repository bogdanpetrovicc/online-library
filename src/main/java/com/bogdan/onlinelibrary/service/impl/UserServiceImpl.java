package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.User;
import com.bogdan.onlinelibrary.repository.UserRepository;
import com.bogdan.onlinelibrary.service.UserService;
import com.bogdan.onlinelibrary.service.generic.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

    protected UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
    }
}
