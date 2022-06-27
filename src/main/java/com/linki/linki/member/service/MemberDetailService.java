package com.linki.linki.member.service;

import com.linki.linki.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {
        return memberRepository.findByMemberID(Long.valueOf(userID))
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("Member %s does not exist", userID)));
    }
}
