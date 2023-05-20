package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.entity.Member;
import com.bogdan.onlinelibrary.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/save")
    public String savePremiumMember(@RequestParam("userId") Integer userId) {
        Member member = memberService.findByUserId(userId);
        memberService.savePremiumMember(member);
        return "redirect:/books";
    }
}
