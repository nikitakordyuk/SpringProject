package org.example.models.book;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    private int bookId;

    private Integer personId;

    @Size(min = 1, max = 100, message = "The name of book should be between 1 and 100 characters")
    private String name;

    @Size(min = 1, max = 100, message = "Author's name should be between 1 and 100 characters")
    private String author;

    @Min(value = 0, message = "The year the book was written cannot be less than 0")
    private int year;

    public Book() {

    }

    public Book(int bookId, String name, String author, int year, int personId) {
        this.bookId = bookId;
        this.name = name;
        this.author = author;
        this.year = year;
        this.personId = personId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getBookId() {
        return bookId;
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

    @Override
    public String toString() {
        return "Book{" +
               "bookId=" + bookId +
               ", personId=" + personId +
               ", name='" + name + '\'' +
               ", author='" + author + '\'' +
               ", year=" + year +
               '}';
    }
}
