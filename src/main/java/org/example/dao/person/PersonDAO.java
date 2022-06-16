package org.example.dao.person;

import org.example.models.book.Book;
import org.example.models.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query(
                        "SELECT * FROM person WHERE id = ?", new BeanPropertyRowMapper<>(Person.class), id
                )
                .stream()
                .findAny()
                .orElse(null);
    }

    public Optional<Person> show(String fullName) {
        return jdbcTemplate.query(
                        "SELECT * FROM person WHERE full_name = ?",
                        new BeanPropertyRowMapper<>(Person.class),
                        fullName
                )
                .stream()
                .findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(full_name, year_of_birth) VALUES (?, ?)",
                person.getFullName(), person.getYearOfBirth());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET full_name = ?, year_of_birth = ? WHERE id = ?",
                updatedPerson.getFullName(),
                updatedPerson.getYearOfBirth(),
                id);
    }

    public List<Book> takenBooks(int id) {
        return jdbcTemplate.query(
                "SELECT name, year FROM person JOIN book b on person.id = b.person_id WHERE person_id = ?",
                new BeanPropertyRowMapper<>(Book.class),
                id
        );
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

}
