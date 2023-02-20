package com.pasquale.BookLibrary.models;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
    private int id;
    @Size(min=2, max=100, message = "Name should be between 2 and 100 characters")
    @NotEmpty(message = "Name should be not empty")
    private String name;
    @NotEmpty(message = "Author should be not empty")
    private String author;
    @Min(value = 1, message = "year should be greater than 0")
    private int year;
    public Book(){}

    public Book(int id, Integer person_id , String name, String author, int year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
