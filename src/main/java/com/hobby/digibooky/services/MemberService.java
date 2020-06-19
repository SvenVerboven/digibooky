package com.hobby.digibooky.services;

import com.hobby.digibooky.domain.exceptions.EmailNotUniqueException;
import com.hobby.digibooky.domain.exceptions.InnsNotUniqueException;
import com.hobby.digibooky.dtos.CreateMemberDto;
import com.hobby.digibooky.dtos.MemberDto;
import com.hobby.digibooky.infrastructure.MemberRepository;
import com.hobby.digibooky.mappers.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberDto saveMember(CreateMemberDto createMemberDto){
        validateMember(createMemberDto);
        return MemberMapper.toDto(memberRepository.save(MemberMapper.toMember(createMemberDto)));
    }

    private void validateMember(CreateMemberDto createMemberDto) {
        validateInns(createMemberDto.getInns());
        validateEmail(createMemberDto.getEmail());
    }

    private void validateEmail(String email) {
        if(memberRepository.findByEmail(email) != null){
            throw new EmailNotUniqueException(email);
        }
    }

    private void validateInns(String inns) {
        if(memberRepository.findByInns(inns) != null){
            throw new InnsNotUniqueException(inns);
        }
    }
}
