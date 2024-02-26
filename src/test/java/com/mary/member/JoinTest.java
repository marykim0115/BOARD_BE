package com.mary.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mary.member.controllers.RequestJoin;
import com.mary.member.entities.Member;
import com.mary.member.repositories.MemberRepository;
import com.mary.member.service.MemberSaveService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.profiles.active=test")
public class JoinTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MemberSaveService saveService;

    @Autowired
    private MemberRepository repository;

    @Test
    @DisplayName("회원가입 기능 테스트")
    void saveServiceTest() {
        RequestJoin form = new RequestJoin();
        form.setEmail("user01@test.org");
        form.setName("사용자01");
        form.setPassword("_aA123456");

        saveService.join(form);

        Member member = repository.findByEmail(form.getEmail()).orElse(null);
        System.out.println(member);
    }


    @Test
    @DisplayName("[통합테스트]회원가입 테스트")
    void joinApiTest() throws Exception {

        RequestJoin form = new RequestJoin();
        form.setEmail("user01@test.org");
        form.setPassword("_aA123456");
        form.setConfirmPassword(form.getPassword());
        form.setName("사용자01");
        form.setAgree(true);

        String params = om.writeValueAsString(form);

        mockMvc.perform(post("/api/v1/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(params))
                .andDo(print());

        Member member = repository.findByEmail(form.getEmail()).orElse(null);
        assertNotNull(member);

        String email = form.getEmail();
        assertEquals(email, member.getEmail());
    }
}
