package com.hobby.digibooky.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hobby.digibooky.domain.Address;
import com.hobby.digibooky.domain.exceptions.EmailNotUniqueException;
import com.hobby.digibooky.domain.exceptions.InnsNotUniqueException;
import com.hobby.digibooky.dtos.CreateMemberDto;
import com.hobby.digibooky.services.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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

    @BeforeEach
    void setUp() {
        createMemberDto = new CreateMemberDto("123456789", "John", "Doe", "John@Doe.com",
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
        createMemberDto.setInns("");

        mvc.perform(MockMvcRequestBuilders.post(DEFAULT_MEMBERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createMemberDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void saveMember_givenNoUniqueInnsNumber_thenReturns400() throws Exception {
        Mockito.when(memberService.saveMember(Mockito.any(CreateMemberDto.class))).thenThrow(InnsNotUniqueException.class);

        mvc.perform(MockMvcRequestBuilders.post(DEFAULT_MEMBERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createMemberDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void saveMember_givenNoEmail_thenReturns400() throws Exception {
        createMemberDto.setEmail("");

        mvc.perform(MockMvcRequestBuilders.post(DEFAULT_MEMBERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createMemberDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void saveMember_givenNoUniqueEmail_thenReturns400() throws Exception {
        Mockito.when(memberService.saveMember(Mockito.any(CreateMemberDto.class))).thenThrow(EmailNotUniqueException.class);

        mvc.perform(MockMvcRequestBuilders.post(DEFAULT_MEMBERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createMemberDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void saveMember_givenNoValidEmail_thenReturns400() throws Exception {
        createMemberDto.setEmail("fake-email-address");

        mvc.perform(MockMvcRequestBuilders.post(DEFAULT_MEMBERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createMemberDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void saveMember_givenNoLastName_thenReturns400() throws Exception {
        createMemberDto.setLastName("");

        mvc.perform(MockMvcRequestBuilders.post(DEFAULT_MEMBERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createMemberDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void saveMember_givenNoCity_thenReturns400() throws Exception {
        Address address = createMemberDto.getAddress();
        address.setCity("");
        createMemberDto.setAddress(address);

        mvc.perform(MockMvcRequestBuilders.post(DEFAULT_MEMBERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createMemberDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
