package ru.kasatchikov.library.models;

import jakarta.validation.constraints.*;

public class Person {
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min=2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;
    @Min(value= 1900, message= "Year should be greater than 1900")
    @Max(value= 2023, message= "Year shouldn't be greater than 2023")
    private int birth_year;
    public Person(){}
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
    public int getBirth_year() {
        return birth_year;
    }
    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }
}
