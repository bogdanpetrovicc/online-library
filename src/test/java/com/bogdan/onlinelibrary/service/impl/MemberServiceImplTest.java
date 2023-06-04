package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.Member;
import com.bogdan.onlinelibrary.entity.UserEntity;
import com.bogdan.onlinelibrary.entity.domain.MemberType;
import com.bogdan.onlinelibrary.repository.MemberRepository;
import com.bogdan.onlinelibrary.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {MemberServiceImpl.class})
public class MemberServiceImplTest {
    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    private Member member;
    private UserEntity user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        user = new UserEntity();
        user.setId(1);
        user.setUsername("test");
        user.setPassword("test");

        member = new Member();
        member.setId(1);
        member.setType(MemberType.STANDARD);
        member.setUser(user);
    }

    @Test
    public void testFindByUserId() {
        when(memberRepository.findByUserId(user.getId())).thenReturn(member);

        Member result = memberService.findByUserId(user.getId());

        assertEquals(member, result);
        verify(memberRepository, times(1)).findByUserId(user.getId());
    }

    @Test
    public void testSavePremiumMember() {
        when(memberRepository.findByUserId(user.getId())).thenReturn(member);

        memberService.savePremiumMember(user.getId());

        assertEquals(MemberType.PREMIUM, member.getType());
        assertEquals(10, member.getDiscount());
        verify(memberRepository, times(1)).findByUserId(user.getId());
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    public void testFindByUsername() {
        String username = "test";
        Member expectedMember = new Member();
        when(memberRepository.findByUser_Username(username)).thenReturn(expectedMember);

        Member result = memberService.findByUsername(username);

        assertEquals(expectedMember, result);
        verify(memberRepository, times(1)).findByUser_Username(username);
    }
}
