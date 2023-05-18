package com.bogdan.onlinelibrary.repository;

import com.bogdan.onlinelibrary.entity.Role;
import com.bogdan.onlinelibrary.repository.generic.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends GenericRepository<Role> {
    Optional<Role> findByName(String name);
}
