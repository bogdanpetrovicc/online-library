package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final UserService userService;

    @ModelAttribute("isAdmin")
    public boolean isAdmin() {
        return userService.isUserAdmin();
    }
}
