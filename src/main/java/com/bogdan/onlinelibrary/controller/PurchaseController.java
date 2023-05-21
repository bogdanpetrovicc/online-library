package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.entity.Member;
import com.bogdan.onlinelibrary.security.SecurityUtil;
import com.bogdan.onlinelibrary.service.BookService;
import com.bogdan.onlinelibrary.service.MemberService;
import com.bogdan.onlinelibrary.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final MemberService memberService;

    //PAGES START
    @GetMapping("")
    public String getPurchasesPage(Model model) {
        model.addAttribute("purchases", purchaseService.findAll());
        return "purchase/purchases";
    }
    //PAGES END

    @PostMapping("/save")
    public String savePurchase(@RequestParam("bookId") Integer bookId,
                               @RequestParam("userId") Integer userId,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
        }
        purchaseService.savePurchase(userId, bookId);
        return "redirect:/books";
    }

    @GetMapping("/my-purchases")
    public String getMyPurchasesPage(Model model) {
        model.addAttribute("purchases", purchaseService.findAllByLoggedInMember());
        return "purchase/my-purchases";
    }
}
