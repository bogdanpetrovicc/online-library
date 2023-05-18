package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.entity.User;
import com.bogdan.onlinelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;

    //PAGES START
    @GetMapping("/login")
    public String getLoginPage() {
        return "login/login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register/register";
    }
    //PAGES END

}
