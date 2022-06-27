package com.linki.linki.member.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity(name = "Member")
@Getter
public class Member implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long memberID;

    @Column(name = "login_id", unique = true, nullable = false, columnDefinition = "varchar(40)")
    private String loginID;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "phone_number", unique = true, nullable = false, columnDefinition = "varchar(15)")
    private String phoneNumber;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(40)")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role", nullable = false, columnDefinition = "varchar(20)")
    private MemberRole memberRole;

    protected Member() {

    }

    @Builder
    public Member(Long memberID, String loginID, String passwordHash, String phoneNumber, String name, MemberRole memberRole) {
        this.memberID = memberID;
        this.loginID = loginID;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.memberRole = memberRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(memberRole.toString()));
    }

    @Override
    public String getPassword() {
        return getPasswordHash();
    }

    @Override
    public String getUsername() {
        return getMemberID().toString();
    }

    // TODO 계정 권한 추가구현
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

