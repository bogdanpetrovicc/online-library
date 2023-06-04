package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.entity.CreditCard;
import com.bogdan.onlinelibrary.entity.UserEntity;
import com.bogdan.onlinelibrary.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {AuthenticationController.class})
public class AuthenticationControllerTest {
    @MockBean
    private UserService userService;
    @MockBean
    private Model model;
    @MockBean
    private BindingResult bindingResult;

    @Autowired
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetLoginPage() {
        String result = authenticationController.getLoginPage();

        assertEquals("login/login", result);
    }

    @Test
    public void testGetRegisterPage() {
        String result = authenticationController.getRegisterPage(model);

        assertEquals("register/register", result);
        verify(model, times(1)).addAttribute(eq("user"), any(UserEntity.class));
        verify(model, times(1)).addAttribute(eq("creditCard"), any(CreditCard.class));
    }

    @Test
    public void testSaveUser_WhenUserDoesNotExist() {
        UserEntity user = new UserEntity();
        when(userService.findByUsername(user.getUsername())).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = authenticationController.saveUser(user, bindingResult);

        assertEquals("book/books", result);
        verify(userService, times(1)).saveUser(user);
        verify(bindingResult, times(1)).hasErrors();
    }

    @Test
    public void testSaveUser_WhenUserExists() {
        UserEntity user = new UserEntity();
        when(userService.findByUsername(user.getUsername())).thenReturn(new UserEntity());

        assertThrows(RuntimeException.class, () -> authenticationController.saveUser(user, bindingResult));
        verify(userService, times(1)).findByUsername(user.getUsername());
        verify(bindingResult, never()).hasErrors();
        verify(userService, never()).saveUser(user);
    }

    @Test
    public void testSaveUser_WhenInvalidUserData() {
        UserEntity user = new UserEntity();
        when(userService.findByUsername(user.getUsername())).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(true);

        assertThrows(RuntimeException.class, () -> authenticationController.saveUser(user, bindingResult));
        verify(userService, never()).saveUser(user);
    }
}
