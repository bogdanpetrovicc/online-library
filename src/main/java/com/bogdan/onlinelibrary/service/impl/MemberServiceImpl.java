package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.Member;
import com.bogdan.onlinelibrary.repository.MemberRepository;
import com.bogdan.onlinelibrary.service.MemberService;
import com.bogdan.onlinelibrary.service.generic.impl.GenericServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class MemberServiceImpl extends GenericServiceImpl<Member> implements MemberService {

    protected MemberServiceImpl(MemberRepository memberRepository) {
        super(memberRepository);
    }
}
