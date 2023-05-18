package com.bogdan.onlinelibrary.repository;

import com.bogdan.onlinelibrary.entity.User;
import com.bogdan.onlinelibrary.repository.generic.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends GenericRepository<User> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
}
