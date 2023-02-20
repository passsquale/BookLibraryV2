package com.pasquale.BookLibrary.models;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Person {
    private int id;
    @Size(min=2, max=100, message = "Name should be between 2 and 100 characters")
    @NotEmpty(message = "Name should be not empty")
    private String name;

    @Min(value = 1900, message = "Birth year should be greater than 1900")
    private int yearOfBirth;
    public Person(){}
    public Person(int id, String name, int yearOfBirth) {
        this.id = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
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
}
