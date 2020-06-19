package com.hobby.digibooky.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hobby.digibooky.domain.Address;
import com.hobby.digibooky.dtos.CreateMemberDto;
import com.hobby.digibooky.dtos.MemberDto;
import com.hobby.digibooky.services.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    public static final String DEFAULT_MEMBERS_URL = "/members";

    @Autowired
    private MockMvc mvc;
    @MockBean
    private MemberService memberService;
    @Autowired
    private ObjectMapper objectMapper;
    private CreateMemberDto createMemberDto;
    private MemberDto memberDto;

    @BeforeEach
    void setUp() {
        createMemberDto = new CreateMemberDto("123456789", "John", "Doe", "John@Doe.com",
                new Address("street", "house number", "postal code", "city"));
        memberDto = new MemberDto(1L,"123456789", "John", "Doe", "John@Doe.com",
                new Address("street", "house number", "postal code", "city"));
    }

    @Test
    void saveMember_givenValidInput_thenReturns201() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(DEFAULT_MEMBERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createMemberDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void saveMember_givenNoInnsNumber_thenReturns400() throws Exception {
        createMemberDto.setInns(null);

        mvc.perform(MockMvcRequestBuilders.post(DEFAULT_MEMBERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createMemberDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
