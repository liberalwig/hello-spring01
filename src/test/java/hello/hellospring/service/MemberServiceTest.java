package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    MemberService memberService = new MemberService();
    MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        memberRepository.classStore();
    }


    @Test // 테스트는 한국어로 써도 좋음. 빌드될 시 테스트코드는 실제코드에 비포함
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("hello");

        // when : 무엇을 검증할 것인가
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test // join의 핵심은 저장이 아니라 중복회원 검증 로직을 잘 타서 예외를 터뜨리는 것까지 보는 것임
    public void 중복_회원_예외() { // 우측상단디버깅 = 디버깅포인트가 존재할 시 디버깅만을 위한 서버
        // given
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1); // memberService에서 member1을 조인했을 시엔 문제 안 발생
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));// 화살표 뒤: 람다로 넘어가야 함

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /*
        try {
            memberService.join(member2);
            fail(); // "예외가 발생해야 합니다."
        } catch (IllegalStateException e) { // 예외가 터지면 catch로 이동
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }

        try {
            memberService.memberRepostioryy.


        */


        // then
    }

    @Test
    void findOne() {
    }
}