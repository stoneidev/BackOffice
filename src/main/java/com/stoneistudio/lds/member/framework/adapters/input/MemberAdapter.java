package com.stoneistudio.lds.member.framework.adapters.input;

import com.stoneistudio.lds.member.application.usecase.MemberUseCase;
import com.stoneistudio.lds.member.domain.member.entity.Member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberAdapter {

    private final MemberUseCase memberUseCase;

    public MemberAdapter(MemberUseCase memberUseCase) {
        this.memberUseCase = memberUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> addMember(@RequestBody Member member) {
        memberUseCase.addMember(member);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Member member = memberUseCase.getMemberById(id);
        return ResponseEntity.ok(member);
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberUseCase.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMember(@PathVariable Long id, @RequestBody Member member) {
        member.setMemberId(id);
        memberUseCase.updateMember(member);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberUseCase.deleteMember(id);
        return ResponseEntity.ok().build();
    }
}
