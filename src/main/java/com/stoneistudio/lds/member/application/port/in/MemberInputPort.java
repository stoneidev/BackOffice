package com.stoneistudio.lds.member.application.port.in;

import com.stoneistudio.lds.member.application.port.out.MemberOutputPort;
import com.stoneistudio.lds.member.application.usecase.MemberUseCase;
import com.stoneistudio.lds.member.domain.member.entity.Member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberInputPort implements MemberUseCase {
    private final MemberOutputPort memberOutputPort;

    public MemberInputPort(MemberOutputPort memberOutputPort) {
        this.memberOutputPort = memberOutputPort;
    }

    @Override
    public void addMember(Member member) {
        memberOutputPort.save(member);
    }

    @Override
    public Member getMemberById(Long memberId) {
        return memberOutputPort.findById(memberId);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberOutputPort.findAll();
    }

    @Override
    public void updateMember(Member member) {
        memberOutputPort.save(member);
    }

    @Override
    public void deleteMember(Long memberId) {
        memberOutputPort.deleteById(memberId);
    }
}
