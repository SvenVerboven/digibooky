package com.hobby.digibooky.infrastructure;

import com.hobby.digibooky.domain.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Member findByInns(String inns);
    Member findByEmail(String email);
}
