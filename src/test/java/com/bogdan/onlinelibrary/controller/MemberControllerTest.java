package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.entity.Member;
import com.bogdan.onlinelibrary.entity.UserEntity;
import com.bogdan.onlinelibrary.entity.domain.MemberType;
import com.bogdan.onlinelibrary.service.MemberService;
import com.bogdan.onlinelibrary.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {MemberController.class})
class MemberControllerTest {
    @MockBean
    private MemberService memberService;
    @MockBean
    private UserService userService;
    @MockBean
    private Model model;
    @Autowired
    private MemberController memberController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMembers() {
        List<Member> members = new ArrayList<>();
        members.add(new Member(new UserEntity(), 1, 0, MemberType.STANDARD));
        members.add(new Member(new UserEntity(), 2, 10, MemberType.PREMIUM));
        when(memberService.findAll()).thenReturn(members);

        String result = memberController.getMembers(model);

        assertEquals("member/members", result);
        verify(model, times(1)).addAttribute("members", members.stream()
                .sorted(Comparator.comparing(Member::getType))
                .collect(Collectors.toList()));
    }

    @Test
    void testGetMemberProfile() {
        UserEntity user = new UserEntity();
        Member member = new Member(user, 1, 0, MemberType.STANDARD);
        when(userService.getLoggedInUser()).thenReturn(user);
        when(memberService.findByUserId(user.getId())).thenReturn(member);

        String result = memberController.getMemberProfile(model);

        assertEquals("member/member-profile", result);
    }

    @Test
    void testSavePremiumMember() {
        int userId = 1;

        String result = memberController.savePremiumMember(userId);

        assertEquals("redirect:/books", result);
        verify(memberService, times(1)).savePremiumMember(userId);
    }
}
