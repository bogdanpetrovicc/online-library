package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.CreditCard;
import com.bogdan.onlinelibrary.entity.Role;
import com.bogdan.onlinelibrary.entity.UserEntity;
import com.bogdan.onlinelibrary.repository.CreditCardRepository;
import com.bogdan.onlinelibrary.repository.RoleRepository;
import com.bogdan.onlinelibrary.repository.UserRepository;
import com.bogdan.onlinelibrary.service.CreditCardService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {CreditCardServiceImpl.class})
public class CreditCardServiceImplTest {
    @MockBean
    private CreditCardRepository creditCardRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;
    @Autowired
    private CreditCardService creditCardService;

    private static final String USERNAME = "test";

    private UserEntity user;
    private Role role;
    private CreditCard creditCard;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        role = new Role(
                1,
                "USER"
        );
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        creditCard = new CreditCard(
                1,
                "123",
                "Bank",
                2000.00
        );
        when(creditCardRepository.save(any(CreditCard.class))).thenReturn(creditCard);

        user = new UserEntity(
                1,
                role,
                creditCard,
                USERNAME,
                "test"
        );
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        creditCardRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void testFindByLoggedInUser_WithExistingUser() {
        // Set up the SecurityContext with the authentication
        Authentication authentication = new UsernamePasswordAuthenticationToken(USERNAME, null);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.ofNullable(user));
        when(creditCardRepository.findById(creditCard.getId())).thenReturn(Optional.ofNullable(creditCard));

//        CreditCard result = creditCardService.findByLoggedInUser();
//
//        assertNotNull(result);
//        assertEquals(creditCard, result);
    }

}
