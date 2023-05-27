package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.entity.CreditCard;
import com.bogdan.onlinelibrary.service.CreditCardService;
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

    //PAGES START
    @GetMapping("")
    public String getCreditCardsPage(Model model) {
        model.addAttribute("creditCard", creditCardService.findByLoggedInUser());
        return "credit-card/update-credit-card";
    }
    //PAGES END

    @PostMapping("/update")
    public String updateCreditCard(@Valid @ModelAttribute("creditCard") CreditCard creditCard,
                                   @RequestParam("creditCardId") Integer creditCardId,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "credit-card/update-credit-card";
        }
        creditCard.setId(creditCardId);
        creditCardService.update(creditCard);

        return "redirect:/books";
    }
}
