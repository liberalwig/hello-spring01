package hello.hellospring.repository;

// Extend : Interface 가 Interface 를 implements 받을 시
// Interface 는 다중 상속 가능
import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);
}
