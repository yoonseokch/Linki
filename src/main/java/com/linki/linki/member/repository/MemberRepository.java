package com.linki.linki.member.repository;

import com.linki.linki.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface MemberRepository extends JpaRepository<Member,String> {

    Optional<Member> findByLoginID(String loginID);
}
