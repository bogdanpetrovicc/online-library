package com.bogdan.onlinelibrary.repository;

import com.bogdan.onlinelibrary.entity.Member;
import com.bogdan.onlinelibrary.repository.generic.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends GenericRepository<Member> {
}
