package com.hobby.digibooky.services;

import com.hobby.digibooky.domain.Address;
import com.hobby.digibooky.domain.Member;
import com.hobby.digibooky.domain.exceptions.EmailNotUnique;
import com.hobby.digibooky.domain.exceptions.InnsNotUniqueException;
import com.hobby.digibooky.dtos.CreateMemberDto;
import com.hobby.digibooky.dtos.MemberDto;
import com.hobby.digibooky.infrastructure.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberService memberService;
    private CreateMemberDto createMemberDto;
    private Member member;

    @BeforeEach
    void setUp() {
        createMemberDto = new CreateMemberDto("123456789", "John", "Doe", "John@Doe.com",
                new Address("street", "house number", "postal code", "city"));
        member = new Member("123456789", "John", "Doe", "John@Doe.com",
                new Address("street", "house number", "postal code", "city"));
        member.setId(1L);
    }

    @Test
    void saveMember_thenReturnMemberDto() {
        Mockito.when(memberRepository.save(Mockito.any(Member.class))).thenReturn(member);

        MemberDto savedMember = memberService.saveMember(createMemberDto);

        Assertions.assertThat(savedMember).isNotNull();
        Assertions.assertThat(savedMember.getId()).isNotNull();
        Assertions.assertThat(savedMember.getInns()).isEqualTo(createMemberDto.getInns());
        Assertions.assertThat(savedMember.getFirstName()).isEqualTo(createMemberDto.getFirstName());
        Assertions.assertThat(savedMember.getLastName()).isEqualTo(createMemberDto.getLastName());
        Assertions.assertThat(savedMember.getEmail()).isEqualTo(createMemberDto.getEmail());
        Assertions.assertThat(savedMember.getAddress()).isEqualTo(createMemberDto.getAddress());
    }

    @Test
    void saveMember_givenNoInns_thenThrowIllegalArgumentThrowException() {
        createMemberDto.setInns(null);
        Assertions.assertThatThrownBy(()-> memberService.saveMember(createMemberDto)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void saveMember_givenNotUniqueInns_thenThrowInnsNotUniqueException() {
        Mockito.when(memberRepository.findByInns(member.getInns())).thenReturn(member);
        Assertions.assertThatThrownBy(()-> memberService.saveMember(createMemberDto)).isInstanceOf(InnsNotUniqueException.class);
    }

    @Test
    void saveMember_givenNoEmail_thenThrowIllegalArgumentException() {
        createMemberDto.setEmail(null);
        Assertions.assertThatThrownBy(()-> memberService.saveMember(createMemberDto)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void saveMember_givenNotUniqueEmail_thenThrowEmailNotUniqueException() {
        Mockito.when(memberRepository.findByEmail(Mockito.anyString())).thenReturn(member);
        Assertions.assertThatThrownBy(()-> memberService.saveMember(createMemberDto)).isInstanceOf(EmailNotUnique.class);
    }
}
