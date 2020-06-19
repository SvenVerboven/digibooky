package com.hobby.digibooky.api;

import com.hobby.digibooky.dtos.CreateMemberDto;
import com.hobby.digibooky.dtos.MemberDto;
import com.hobby.digibooky.services.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/members")
public class MemberController {

    private final Logger memberControllerLogger = LoggerFactory.getLogger(MemberController.class);
    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto saveMember(@Valid @RequestBody CreateMemberDto createMemberDto) {
        memberControllerLogger.info("Saved member");
        return memberService.saveMember(createMemberDto);
    }
}
