package com.pasquale.BookLibrary.dao;

import com.pasquale.BookLibrary.models.Book;
import com.pasquale.BookLibrary.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }
    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }
    public Optional<Person> show(String name){
        return jdbcTemplate.query("SELECT * FROM Person WHERE name=?", new Object[]{name}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
    public void save(Person person){
        jdbcTemplate.update("INSERT INTO Person(name, year_of_birth) VALUES(?, ?)", person.getName(), person.getYearOfBirth());
    }
    public void edit(Person updatePerson, int id){
        jdbcTemplate.update("UPDATE Person SET name=?, year_of_birth=? WHERE id=?", updatePerson.getName(), updatePerson.getYearOfBirth(), id);
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
    public List<Book> getBooks(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }


}
