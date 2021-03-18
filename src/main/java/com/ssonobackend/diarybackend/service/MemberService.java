package com.ssonobackend.diarybackend.service;

import com.ssonobackend.diarybackend.domain.Member;
import com.ssonobackend.diarybackend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 조회시 데이터 변경 x
@RequiredArgsConstructor // final을 가진 필드만 생성자를 만들어줌
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = false)
    // 회원가입
    public Long join(Member member) {
        validateDuplicateMember(member);    // 중복 회원 검증
        memberRepository.save(member);
        // 문제가 없으면 id 반환환
        return member.getId();
    }

    // 중복 회원 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    // 회원 전체 조회
    public List<Member> findMember() {
        return memberRepository.findAll();
    }

    // 회원 id 검색
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
