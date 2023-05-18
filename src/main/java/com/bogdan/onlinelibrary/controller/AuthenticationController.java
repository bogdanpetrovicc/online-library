package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;


}
