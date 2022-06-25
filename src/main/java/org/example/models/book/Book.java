package org.example.models.book;

import org.example.models.person.Person;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @Column(name = "person_id")
    private Integer personId;

    @Column(name = "name")
    @Size(min = 1, max = 100, message = "The name of book should be between 1 and 100 characters")
    private String name;

    @Column(name = "author")
    @Size(min = 1, max = 100, message = "Author's name should be between 1 and 100 characters")
    private String author;

    @Column(name = "year")
    @Min(value = 0, message = "The year the book was written cannot be less than 0")
    private int year;

    @Column(name = "took_at")
    private Timestamp tookAt;

    @Transient
    boolean late;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Person owner;

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person person) {
        this.owner = person;
    }


    public Book() {

    }

    public Book(String name, String author, int year, int personId, Timestamp tookAt) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.personId = personId;
        this.tookAt = tookAt;
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

    public Timestamp getTookAt() {
        return tookAt;
    }

    public void setTookAt(Timestamp tookAt) {
        this.tookAt = tookAt;
    }

    public boolean isLate() {
        return (new Timestamp(System.currentTimeMillis()).getTime() - getTookAt().getTime()) > 864000000;
    }

    public void setLate(boolean late) {
        this.late = late;
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
