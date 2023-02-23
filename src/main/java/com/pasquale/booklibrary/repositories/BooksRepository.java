package com.pasquale.booklibrary.repositories;

import com.pasquale.booklibrary.models.Book;
import com.pasquale.booklibrary.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwner(Person person);
    List<Book> findBooksByTitleStartingWithIgnoreCase(String query);
}
