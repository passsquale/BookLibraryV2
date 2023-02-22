package com.pasquale.BookLibrary.repositories;

import com.pasquale.BookLibrary.models.Book;
import com.pasquale.BookLibrary.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwner(Person person);
}
