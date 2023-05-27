package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.entity.UserEntity;
import com.bogdan.onlinelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final UserService userService;

    // ATTRIBUTE
    @ModelAttribute("isAdmin")
    public boolean isAdmin() {
        return userService.isUserAdmin();
    }

    @ModelAttribute("user")
    public UserEntity getLoggedInUser() {
        return userService.getLoggedInUser();
    }
}
