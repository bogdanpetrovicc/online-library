package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.Role;
import com.bogdan.onlinelibrary.entity.UserEntity;
import com.bogdan.onlinelibrary.repository.RoleRepository;
import com.bogdan.onlinelibrary.repository.UserRepository;
import com.bogdan.onlinelibrary.repository.generic.GenericRepository;
import com.bogdan.onlinelibrary.service.CreditCardService;
import com.bogdan.onlinelibrary.service.UserService;
import com.bogdan.onlinelibrary.service.generic.impl.GenericServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends GenericServiceImpl<UserEntity> implements UserService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CreditCardService creditCardService;

    protected UserServiceImpl(GenericRepository<UserEntity> genericRepository, UserRepository userRepository1, RoleRepository roleRepository, PasswordEncoder passwordEncoder, CreditCardService creditCardService) {
        super(genericRepository);
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.creditCardService = creditCardService;
    }

    @Override
    public UserEntity findByUsername(String username) {
        return ((UserRepository) genericRepository).findByUsername(username)
                .orElse(null);
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        UserEntity newUser = new UserEntity();

        Role roleUser = roleRepository.findByName(Role.USER).orElse(null);
        newUser.setRole(roleUser);

        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        creditCardService.save(user.getCreditCard());
        newUser.setCreditCard(user.getCreditCard());

        return genericRepository.save(newUser);
    }
}
