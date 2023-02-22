package com.pasquale.BookLibrary.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @Size(min=2, max=100, message = "Name should be between 2 and 100 characters")
    @NotEmpty(message = "Name should be not empty")
    private String name;

    @Column(name = "year_of_birth")
    @Min(value = 1900, message = "Year of birth should be greater than 1900")
    private int yearOfBirth;

    @OneToMany(mappedBy = "owner")
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<Book> books;

    public Person(){}
    public Person(int id, String name, int yearOfBirth) {
        this.id = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }
    public void addBook(Book book){
        this.getBooks().add(book);
        book.setOwner(this);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getBooks() {
        if(books == null){
            books = new ArrayList<>();
        }
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
