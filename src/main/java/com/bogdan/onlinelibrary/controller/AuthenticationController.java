package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.entity.CreditCard;
import com.bogdan.onlinelibrary.entity.UserEntity;
import com.bogdan.onlinelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class AuthenticationController {
    private final UserService userService;

    // PAGES START
    @GetMapping("/")
    public String redirectToLoginPage() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login/login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("user", new UserEntity());
        model.addAttribute("creditCard", new CreditCard());
        return "register/register";
    }
    // PAGES END

    @PostMapping("/register/save")
    public String saveUser(@Valid @ModelAttribute("user") UserEntity user, BindingResult result) {
        Optional<UserEntity> existingUser = Optional.ofNullable(userService.findByUsername(user.getUsername()));

        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists!");
        }
        if (result.hasErrors()) {
            throw new RuntimeException("Invalid user data!");
        }
        userService.saveUser(user);
        return "login/login";
    }

}
