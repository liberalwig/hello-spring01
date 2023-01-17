package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository;



    /*
  회원 가입
   */
    public Long join(Member member) {
        // 같은 이름의 중복회원은 X
        Optional<Member> result = memberRepository.findByName(member.getName()); // Optional을 바로 반환하는 건 안 예쁨
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
        memberRepository.save(member);
        return member.getId(); // id만 반환하는 걸로 스펙을 잡음
    }

    // refactor this의 method에 들어가서 중복회원검증하는 클래스를 만들 수 있음
    // validateDuplicateMember(member);
    // memberRepository.save(member);
    // return member.getId();

    // 이렇게 먼저 등록해서 join메소드 들어오면 저 검증 메소드를 먼저 할 수 있음

    // 전체 회원 조회. 서비스는 비즈니스에 의존적, 레파지토리는 개발쪽에 의존적.
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }


}