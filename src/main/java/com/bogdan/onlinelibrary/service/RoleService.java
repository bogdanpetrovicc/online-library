package com.bogdan.onlinelibrary.service;

import com.bogdan.onlinelibrary.entity.Role;
import com.bogdan.onlinelibrary.service.generic.GenericService;

public interface RoleService extends GenericService<Role> {

    Role findByName(String name);
}
