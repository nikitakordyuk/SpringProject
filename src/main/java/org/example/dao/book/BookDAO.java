package org.example.dao.book;

import org.example.models.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query(
                        "SELECT * FROM Book WHERE book_id = ?", new BeanPropertyRowMapper<>(Book.class), id
                )
                .stream()
                .findAny()
                .orElse(null);
    }

    public Optional<Book> showByName(String name) {
        return jdbcTemplate.query(
                        "SELECT * FROM Book WHERE name = ?", new BeanPropertyRowMapper<>(Book.class), name
                )
                .stream()
                .findAny();
    }

    public Optional<Book> showByAuthor(String author) {
        return jdbcTemplate.query(
                        "SELECT * FROM Book WHERE author = ?", new BeanPropertyRowMapper<>(Book.class), author
                )
                .stream()
                .findAny();
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES (?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET name = ?, author = ?, year = ? WHERE book_id = ?",
                updatedBook.getName(),
                updatedBook.getAuthor(),
                updatedBook.getYear(),
                id);
    }

    public void assignBookToPerson(int personId, int bookId) {
        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE book_id = ?", personId, bookId);
    }

    public void deAssignBookFromPerson(Integer personId, int bookId) {
        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE book_id = ?", personId, bookId);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id = ?", id);
    }

}
