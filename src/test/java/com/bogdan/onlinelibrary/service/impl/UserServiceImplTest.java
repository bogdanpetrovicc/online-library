package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.CreditCard;
import com.bogdan.onlinelibrary.entity.Member;
import com.bogdan.onlinelibrary.entity.Role;
import com.bogdan.onlinelibrary.entity.UserEntity;
import com.bogdan.onlinelibrary.repository.RoleRepository;
import com.bogdan.onlinelibrary.repository.UserRepository;
import com.bogdan.onlinelibrary.security.SecurityUtil;
import com.bogdan.onlinelibrary.service.CreditCardService;
import com.bogdan.onlinelibrary.service.MemberService;
import com.bogdan.onlinelibrary.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {UserServiceImpl.class})
public class UserServiceImplTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private CreditCardService creditCardService;
    @MockBean
    private MemberService memberService;

    @Autowired
    private UserService userService;

    private Role role;
    private UserEntity user;
    private CreditCard creditCard;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        role = new Role();
        role.setId(2);
        role.setName(Role.USER);

        creditCard = new CreditCard();
        creditCard.setId(1);
        creditCard.setCardNumber("123");
        creditCard.setBalance(50000.00);

        user = new UserEntity();
        user.setId(1);
        user.setUsername("test");
        user.setPassword("test");
        user.setRole(role);
        user.setCreditCard(creditCard);
    }

    @Test
    public void testFindByUsername_WhenUserExists() {
        String username = "test";
        UserEntity expectedUser = new UserEntity();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        UserEntity result = userService.findByUsername(username);

        assertEquals(expectedUser, result);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testFindByUsername_WhenUserDoesNotExist() {
        String username = "test";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        UserEntity result = userService.findByUsername(username);

        assertNull(result);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testSaveUser() {
        when(roleRepository.findByName(Role.USER)).thenReturn(Optional.of(role));

        when(creditCardService.save(creditCard)).thenReturn(creditCard);

        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        UserEntity savedUser = userService.saveUser(user);

        assertEquals(role, savedUser.getRole());
        assertEquals(creditCard, savedUser.getCreditCard());
        verify(roleRepository, times(1)).findByName(Role.USER);
        verify(creditCardService, times(1)).save(creditCard);
        verify(userRepository, times(1)).save(savedUser);
    }


}
