package com.mary.member.service;

import com.mary.member.constants.Authority;
import com.mary.member.controllers.RequestJoin;
import com.mary.member.entities.Member;
import com.mary.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSaveService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    public void join(RequestJoin form) {

        String hash = encoder.encode(form.getPassword());

        Member member = Member.builder()
                .email(form.getEmail())
                .name(form.getName())
                .password(hash)
                .authority(Authority.USER)
                .lock(false)
                .enable(true)
                .build();

        save(member);
    }

    public void save(Member member) {

        memberRepository.saveAndFlush(member);
    }

}