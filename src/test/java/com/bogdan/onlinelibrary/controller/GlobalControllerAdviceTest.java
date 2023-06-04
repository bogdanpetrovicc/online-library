package com.bogdan.onlinelibrary.controller;

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
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {GlobalControllerAdvice.class})
class GlobalControllerAdviceTest {
    @MockBean
    private UserService userService;
    @MockBean
    private Model model;
    @Autowired
    private GlobalControllerAdvice globalControllerAdvice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsAdmin() {
        when(userService.isUserAdmin()).thenReturn(true);

        boolean result = globalControllerAdvice.isAdmin();

        assertTrue(result);
        verify(userService, times(1)).isUserAdmin();
    }

    @Test
    void testGetLoggedInUser() {
        UserEntity user = new UserEntity();
        when(userService.getLoggedInUser()).thenReturn(user);

        UserEntity result = globalControllerAdvice.getLoggedInUser();

        assertEquals(user, result);
        verify(userService, times(1)).getLoggedInUser();
    }

    @Test
    void testHandleException() {
        Exception ex = new RuntimeException("Test exception");

        String result = globalControllerAdvice.handleException(ex, model);

        assertEquals("error/error", result);
        verify(model, times(1)).addAttribute("errorMessage", "An error occurred: " + ex.getMessage());
    }
}
