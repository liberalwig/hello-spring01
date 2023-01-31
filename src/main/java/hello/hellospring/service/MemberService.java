package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        validateDuplicateMember(member); // 같은 이름의 중복 회원 검증
        memberRepository.save(member); // 통과시 바로 저장
        return member.getId();

    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

        /*
        Optional 쓰지 않을 시에는 ifPresent 같은 걸 못 쓰는데 안 쓰고 싶을 시엔
        Member member1 = result.get();
        member1 꺼내서 쓰기

        findByName 을 하면 그 결과는 옵셔널이니까 바로 ifPresent 가능함. Optional <Member> . ifPresent 격
        Optional<Member> result = memberRepository.findByName(member.getName());  가능하지만 옵셔널 안 예뻐서 빼
        */


    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
