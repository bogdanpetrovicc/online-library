package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    //PAGES START
    @GetMapping("")
    public String getPurchasesPage(Model model) {
        model.addAttribute("purchases", purchaseService.findAll());
        return "purchase/purchases";
    }
    //PAGES END
}
