package org.example.repositories;

import org.example.models.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findBookByAuthor(String author);

    List<Book> findBookByName(String name);

    List<Book> findByPersonId(int id);

    List<Book> findBookByNameStartingWith(String startingWith);
}
