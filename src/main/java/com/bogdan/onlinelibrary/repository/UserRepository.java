package com.bogdan.onlinelibrary.repository;

import com.bogdan.onlinelibrary.entity.User;
import com.bogdan.onlinelibrary.repository.generic.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GenericRepository<User> {
}
