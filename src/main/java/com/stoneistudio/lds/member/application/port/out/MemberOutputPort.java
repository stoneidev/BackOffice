package com.stoneistudio.lds.member.application.port.out;

import java.util.List;

import com.stoneistudio.lds.member.domain.member.entity.Member;

public interface MemberOutputPort {
    void save(Member member);

    Member findById(Long memberId);

    List<Member> findAll();

    void deleteById(Long memberId);
}
