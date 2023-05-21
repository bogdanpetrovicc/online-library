package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.Member;
import com.bogdan.onlinelibrary.entity.domain.MemberType;
import com.bogdan.onlinelibrary.repository.MemberRepository;
import com.bogdan.onlinelibrary.repository.generic.GenericRepository;
import com.bogdan.onlinelibrary.service.MemberService;
import com.bogdan.onlinelibrary.service.generic.impl.GenericServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class MemberServiceImpl extends GenericServiceImpl<Member> implements MemberService {

    protected MemberServiceImpl(GenericRepository<Member> genericRepository) {
        super(genericRepository);
    }

    @Override
    public Member findByUserId(Integer userId) {
        return ((MemberRepository) genericRepository).findByUserId(userId);
    }

    @Override
    public void savePremiumMember(Integer userId) {
        Member member = findByUserId(userId);
        member.setType(MemberType.PREMIUM);
        genericRepository.save(member);
    }
}
