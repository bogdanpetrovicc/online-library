package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.entity.Author;
import com.bogdan.onlinelibrary.entity.Book;
import com.bogdan.onlinelibrary.entity.CreditCard;
import com.bogdan.onlinelibrary.service.CreditCardService;
import com.bogdan.onlinelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/credit-cards")
public class CreditCardController {
    private final CreditCardService creditCardService;
    private final UserService userService;

    //PAGES START
    @GetMapping("")
    public String getCreditCardsPage(@RequestParam(value = "userId", required = false) Integer userId, Model model) {
        CreditCard creditCard = userId != null ? userService.findById(userId).getCreditCard() : new CreditCard();
        model.addAttribute("creditCard", creditCard);
        return "credit-card/update-credit-card";
    }
    //PAGES END

    @PostMapping("/update")
    public String updateBook(@Valid @ModelAttribute("creditCard") CreditCard creditCard,
                             @RequestParam("creditCardId") Integer creditCardId,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "credit-card/update-credit-card";
        }
        creditCard.setId(creditCardId);
        creditCardService.update(creditCard);

        return "redirect:/books";
    }
}
