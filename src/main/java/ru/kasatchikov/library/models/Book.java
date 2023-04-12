package ru.kasatchikov.library.models;

import jakarta.validation.constraints.*;

public class Book {
    @NotEmpty(message = "Name should not be empty")
    @Size(min= 2, max= 100, message = "Name should be between 2 to 100 characters")
    private String name;
    @NotEmpty(message = "Author should not be empty")
    @Size(min= 2, max= 100, message = "Author should be between 2 to 100 characters")
    private String author;
    private int book_id;
    @Min(value= 0, message = "Year should be greater than 0")
    @Max(value= 2023, message = "Year shouldn't be greater than 2023")
    private int year;
    public Book(){}
    public Book(String name, String author, int book_id, int year) {
        this.name = name;
        this.author = author;
        this.book_id = book_id;
        this.year = year;
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
    public int getBook_id() {
        return book_id;
    }
    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
}
