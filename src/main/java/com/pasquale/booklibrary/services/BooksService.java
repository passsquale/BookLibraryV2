package com.pasquale.booklibrary.services;

import com.pasquale.booklibrary.models.Book;
import com.pasquale.booklibrary.models.Person;
import com.pasquale.booklibrary.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }
    public List<Book> findAll(){
        return booksRepository.findAll(Sort.by("title"));
    }
    public Book findOne(int id){
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }
    public Person getBookOwner(int id){
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }
    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }
    @Transactional
    public void update(int id, Book updatedBook){
        Book bookToBeUpdated = booksRepository.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());
        booksRepository.save(updatedBook);
    }
    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }
    @Transactional
    public void release(int id){
        Optional<Book> book = booksRepository.findById(id);
        book.get().setOwner(null);
        book.get().setExpired(false);
        book.get().setTakenAt(null);
    }
    @Transactional
    public void assign(int id, Person person){
        Optional<Book> book = booksRepository.findById(id);
        book.get().setTakenAt(new Date());
        person.addBook(book.get());
    }
    public List<Book> searchByTitle(String query){
        return booksRepository.findBooksByTitleStartingWithIgnoreCase(query);
    }
}
