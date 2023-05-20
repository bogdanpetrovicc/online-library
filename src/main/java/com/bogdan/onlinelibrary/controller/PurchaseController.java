package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.entity.Book;
import com.bogdan.onlinelibrary.entity.Member;
import com.bogdan.onlinelibrary.entity.Purchase;
import com.bogdan.onlinelibrary.entity.domain.MemberType;
import com.bogdan.onlinelibrary.service.BookService;
import com.bogdan.onlinelibrary.service.MemberService;
import com.bogdan.onlinelibrary.service.PurchaseService;
import com.bogdan.onlinelibrary.service.generic.impl.GenericServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;


@Controller
@RequiredArgsConstructor
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final MemberService memberService;
    private final BookService bookService;

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

        Member member = memberService.findByUserId(userId);
        Book book = bookService.findById(bookId);

        if (member.getType() == MemberType.PREMIUM) {
            book.setPrice(book.getPrice() * member.getDiscount() / 100);
        }

        if (member.getUser().getCreditCard().getBalance() >= book.getPrice()) {
            purchaseService.save(new Purchase(
                    book,
                    member,
                    LocalDate.now(),
                    book.getPrice()
            ));
        } else {
            model.addAttribute("error", "Not enough money on your credit card!");
        }

        return "redirect:/books";
    }

    @GetMapping("/my-purchases")
    public String getMyPurchasesPage(@RequestParam("userId") Integer userId, Model model) {
        Member member = memberService.findByUserId(userId);
        model.addAttribute("purchases", purchaseService.findAllByMemberId(member.getId()));
        return "purchase/my-purchases";
    }
}
