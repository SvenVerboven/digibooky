package com.hobby.digibooky.infrastructure;

import com.hobby.digibooky.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {}
