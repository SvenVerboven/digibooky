package com.hobby.digibooky.infrastructure;

import com.hobby.digibooky.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Member findByInns(String inns);
    Member findByEmail(String email);
}
