package org.example.services;

import org.example.models.book.Book;
import org.example.models.person.Person;
import org.example.repositories.BooksRepository;
import org.example.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    private final BooksRepository booksRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BooksRepository booksRepository) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
    }

    public List<Person> peopleList() {
        return peopleRepository.findAll();
    }

    public Person findPerson(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    public Optional<Person> findByFullName(String fullName) {
        return peopleRepository.findByFullName(fullName);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    public List<Book> takenBooks(int id) {
        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            return person.get().getBooks();
        }

        return Collections.emptyList();
    }

    @Transactional
    public boolean isLate(Book book) {
        return (new Timestamp(System.currentTimeMillis()).getTime() - book.getTookAt().getTime()) > 864000000;
    }

    @Transactional
    public void delete(int id) {
        List<Book> byPersonId = booksRepository.findByPersonId(id);
        for (Book book : byPersonId) {
            book.setPersonId(null);
        }

        peopleRepository.deleteById(id);
    }
}
