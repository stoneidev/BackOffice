package com.stoneistudio.lds.member.application.usecase;

import com.stoneistudio.lds.member.domain.member.entity.Member;

import java.util.List;

public interface MemberUseCase {
    void addMember(Member member);

    Member getMemberById(Long memberId);

    List<Member> getAllMembers();

    void updateMember(Member member);

    void deleteMember(Long memberId);
}
