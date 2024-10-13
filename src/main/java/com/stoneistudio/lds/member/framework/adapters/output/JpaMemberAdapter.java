package com.stoneistudio.lds.member.framework.adapters.output;

import com.stoneistudio.lds.member.application.port.out.MemberOutputPort;
import com.stoneistudio.lds.member.domain.member.entity.Member;

import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JpaMemberAdapter implements MemberOutputPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Member member) {
        if (member.getMemberId() == null) {
            entityManager.persist(member);
        } else {
            entityManager.merge(member);
        }
    }

    @Override
    public Member findById(Long memberId) {
        return entityManager.find(Member.class, memberId);
    }

    @Override
    public List<Member> findAll() {
        return entityManager.createQuery("SELECT m FROM Member m", Member.class).getResultList();
    }

    @Override
    public void deleteById(Long memberId) {
        Member member = findById(memberId);
        if (member != null) {
            entityManager.remove(member);
        }
    }
}
