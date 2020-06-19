package com.hobby.digibooky.infrastructure;

import com.hobby.digibooky.domain.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
