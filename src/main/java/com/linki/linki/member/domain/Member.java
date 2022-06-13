package com.linki.linki.member.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity(name = "Member")
@Getter
public class Member {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long memberID;

    @Column(name = "login_id", unique = true, nullable = false, columnDefinition = "varchar(40)")
    private String loginID;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "phone_number", nullable = false, columnDefinition = "varchar(15)")
    private String phoneNumber;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(40)")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role" , nullable = false, columnDefinition = "varchar(20)")
    private MemberRole memberRole;

    @Builder
    public Member(Long memberID, String loginID, String passwordHash, String phoneNumber, String name, MemberRole memberRole) {
        this.memberID = memberID;
        this.loginID = loginID;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.memberRole = memberRole;
    }
}

