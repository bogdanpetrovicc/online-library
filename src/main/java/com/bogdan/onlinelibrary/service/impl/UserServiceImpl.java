package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.Member;
import com.bogdan.onlinelibrary.entity.Role;
import com.bogdan.onlinelibrary.entity.UserEntity;
import com.bogdan.onlinelibrary.entity.domain.MemberType;
import com.bogdan.onlinelibrary.repository.RoleRepository;
import com.bogdan.onlinelibrary.repository.UserRepository;
import com.bogdan.onlinelibrary.repository.generic.GenericRepository;
import com.bogdan.onlinelibrary.security.SecurityUtil;
import com.bogdan.onlinelibrary.service.CreditCardService;
import com.bogdan.onlinelibrary.service.MemberService;
import com.bogdan.onlinelibrary.service.UserService;
import com.bogdan.onlinelibrary.service.generic.impl.GenericServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends GenericServiceImpl<UserEntity> implements UserService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CreditCardService creditCardService;
    private final MemberService memberService;

    protected UserServiceImpl(GenericRepository<UserEntity> genericRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, CreditCardService creditCardService, MemberService memberService) {
        super(genericRepository);
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.creditCardService = creditCardService;
        this.memberService = memberService;
    }

    @Override
    public UserEntity findByUsername(String username) {
        return ((UserRepository) genericRepository).findByUsername(username)
                .orElse(null);
    }

    @Override
    public UserEntity getLoggedInUser() {
        String username = SecurityUtil.getSessionUser();
        return findByUsername(username);
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        UserEntity newUser = new UserEntity();

        // set role
        Role roleUser = roleRepository.findByName(Role.USER).orElse(null);
        newUser.setRole(roleUser);

        // set credit card
        creditCardService.save(user.getCreditCard());
        newUser.setCreditCard(user.getCreditCard());

        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        genericRepository.save(newUser);

        // set member
        memberService.save(new Member(
                newUser,
                newUser.getId(),
                0,
                MemberType.STANDARD
        ));

        return newUser;
    }

    @Override
    public boolean isUserAdmin() {
        return getLoggedInUser() != null && getLoggedInUser().getRole().getName().equals(Role.ADMIN);
    }
}
