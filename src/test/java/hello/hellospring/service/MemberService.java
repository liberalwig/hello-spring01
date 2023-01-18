package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // 같은 이름의 중복회원은 X
        validateDuplicateMember(member); // 중복  회원 검증
        memberRepository.save(member);
        return member.getId(); // id만 반환하는 걸로 스펙을 잡음
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()) // Optional 바로 반환하는 건 안 예뻐서 아래와 같이 정리
            .ifPresent(m-> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    /**
     * 전체 회원 조회 : 서비스는 비즈니스로직 용어를, 리포지토리는 기계적개발 용어 선호
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}