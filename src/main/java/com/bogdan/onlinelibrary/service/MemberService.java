package com.bogdan.onlinelibrary.service;

import com.bogdan.onlinelibrary.entity.Member;
import com.bogdan.onlinelibrary.service.generic.GenericService;

public interface MemberService extends GenericService<Member> {

    Member findByUserId(Integer userId);

    void savePremiumMember(Member member);
}
