package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.exception.NotEnoughMoneyException;
import com.bogdan.onlinelibrary.repository.MemberRepository;
import com.bogdan.onlinelibrary.service.MemberService;
import com.bogdan.onlinelibrary.service.PurchaseService;
import com.bogdan.onlinelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final MemberService memberService;
    private final UserService userService;

    //PAGES START
    @GetMapping("")
    public String getPurchasesPage(Model model) {
        model.addAttribute("purchases", purchaseService.findAll());
        return "purchase/purchases";
    }
    //PAGES END

    @PostMapping("/save")
    public String savePurchase(@RequestParam("bookId") Integer bookId,
                               @RequestParam("userId") Integer userId) {
        try {
            purchaseService.savePurchase(userId, bookId);
            return "redirect:/purchases/my-purchases";
        } catch (NotEnoughMoneyException ex) {
            throw new RuntimeException("Not enough money!");
        }
    }

    @GetMapping("/my-purchases")
    public String getMyPurchasesPage(Model model) {
        model.addAllAttributes(Map.of(
                "purchases", purchaseService.findAllByLoggedInMember(),
                "member", memberService.findByUserId(userService.getLoggedInUser().getId())
        ));
        return "purchase/my-purchases";
    }
}
