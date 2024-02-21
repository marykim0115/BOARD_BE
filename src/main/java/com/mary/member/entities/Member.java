package com.mary.member.entities;

import com.mary.commons.entities.Base;
import com.mary.member.constants.Authority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor @AllArgsConstructor
public class Member extends Base {
    @Id @GeneratedValue
    private Long seq;

    @Column(length=80, unique = true, nullable = false)
    private String email;

    @Column(length=65, nullable = false)
    private String password;

    @Column(length=40, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable = false)
    private Authority authority = Authority.USER;

    private boolean lock;
    private boolean enable;
}