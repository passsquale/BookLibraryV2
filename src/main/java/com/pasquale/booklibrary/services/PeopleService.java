package com.pasquale.booklibrary.services;

import com.pasquale.booklibrary.models.Book;
import com.pasquale.booklibrary.models.Person;
import com.pasquale.booklibrary.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    public List<Person> findAll(){
        return peopleRepository.findAll(Sort.by("name"));
    }

    public Person findOne(int id){
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }
    public Optional<Person> findOne(String name){
        return peopleRepository.findByName(name);
    }
    public List<Book> findBooks(int id){
        Optional<Person> person = peopleRepository.findById(id);
        if(person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            person.get().getBooks().forEach(book -> {
                long diff = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                if(diff > 864000000){ // 10 days
                    book.setExpired(true);
                }
            });
        }
        return person.get().getBooks();
    }

    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }
    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public List<Person> searchByName(String query){
        return peopleRepository.findPersonByNameStartingWithIgnoreCase(query);
    }
}
