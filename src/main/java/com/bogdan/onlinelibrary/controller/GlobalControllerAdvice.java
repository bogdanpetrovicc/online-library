package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.entity.UserEntity;
import com.bogdan.onlinelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final UserService userService;

    // ATTRIBUTES
    @ModelAttribute("isAdmin")
    public boolean isAdmin() {
        return userService.isUserAdmin();
    }

    @ModelAttribute("user")
    public UserEntity getLoggedInUser() {
        return userService.getLoggedInUser();
    }

    // EXCEPTION HANDLING
    @ExceptionHandler(RuntimeException.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "An error occurred: " + ex.getMessage());
        return "error/error";
    }
}
