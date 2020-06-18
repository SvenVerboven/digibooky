package com.hobby.digibooky.mappers;

import com.hobby.digibooky.domain.Member;
import com.hobby.digibooky.dtos.CreateMemberDto;
import com.hobby.digibooky.dtos.MemberDto;

public class MemberMapper {

    private MemberMapper(){}

    public static Member toMember(CreateMemberDto createMemberDto){
        return new Member(
                createMemberDto.getInns(),
                createMemberDto.getFirstName(),
                createMemberDto.getLastName(),
                createMemberDto.getEmail(),
                createMemberDto.getAddress()
        );
    }

    public static MemberDto toDto(Member member){
        return new MemberDto(
                member.getId(),
                member.getInns(),
                member.getFirstName(),
                member.getLastName(),
                member.getEmail(),
                member.getAddress()
        );
    }
}
