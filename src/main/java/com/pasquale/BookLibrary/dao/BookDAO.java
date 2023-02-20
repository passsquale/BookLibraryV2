package com.pasquale.BookLibrary.dao;

import com.pasquale.BookLibrary.models.Book;
import com.pasquale.BookLibrary.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }
    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }
    public void save(Book book){
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES(?, ?, ?)", book.getName(), book.getAuthor(),
                book.getYear());
    }
    public void edit(Book book, int id){
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE id=?", book.getName(), book.getAuthor(),
                book.getYear(), id);
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }
    public List<Book> books_person(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }
    public Person showPerson(int id){
        return jdbcTemplate.query("SELECT person.id, person.name, person.year_of_birth from book join person" +
                " on person.id = book.person_id where book.id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }
    public void release(int id){
        jdbcTemplate.update("UPDATE book SET person_id=null WHERE id=?", id);
    }
    public void assign(int id, int person_id){
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?", person_id, id);
    }
}
