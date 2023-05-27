package com.bogdan.onlinelibrary.service;

import com.bogdan.onlinelibrary.entity.UserEntity;
import com.bogdan.onlinelibrary.service.generic.GenericService;

public interface UserService extends GenericService<UserEntity> {
    UserEntity findByUsername(String username);

    void saveUser(UserEntity user);

    boolean isUserAdmin();
}
