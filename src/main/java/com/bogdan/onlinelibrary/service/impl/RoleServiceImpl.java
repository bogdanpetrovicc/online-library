package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.Role;
import com.bogdan.onlinelibrary.repository.BookRepository;
import com.bogdan.onlinelibrary.repository.RoleRepository;
import com.bogdan.onlinelibrary.repository.generic.GenericRepository;
import com.bogdan.onlinelibrary.service.RoleService;
import com.bogdan.onlinelibrary.service.generic.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends GenericServiceImpl<Role> implements RoleService {

    protected RoleServiceImpl(GenericRepository<Role> genericRepository) {
        super(genericRepository);
    }

    @Override
    public Role findByName(String name) {
        return ((RoleRepository) genericRepository).findByName(name)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }
}
