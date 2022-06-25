package org.example.services;

import org.example.models.book.Book;
import org.example.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> booksList() {
        return booksRepository.findAll();
    }

    public List<Book> certainAmount(List<Book> list, int booksPerPage, int pageForOutput) {
        List<Book> res = new ArrayList<>();

        int pagesAvailable = list.size() / booksPerPage;

        if (booksPerPage > list.size()) booksPerPage = list.size();
        if (pageForOutput >= pagesAvailable) pageForOutput = pagesAvailable - 1;

        for (int i = pageForOutput * booksPerPage; i < pageForOutput * booksPerPage + booksPerPage; i++) {
            res.add(list.get(i));
        }

        return res;
    }

    public Book findBook(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    public List<Book> findByName(String name) {
        return booksRepository.findBookByName(name);
    }

    public List<Book> findByAuthor(String author) {
        return booksRepository.findBookByAuthor(author);
    }

    public List<Book> findByPersonId(int id) {
        return booksRepository.findByPersonId(id);
    }

    public List<Book> search(String startingWith) {
        return booksRepository.findBookByNameStartingWith(startingWith);
    }

    public List<Book> sort(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparingInt(Book::getYear))
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setBookId(id);

        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void assignBookToPerson(int personId, int bookId) {
        Optional<Book> book = booksRepository.findById(bookId);

        book.ifPresent(val -> val.setPersonId(personId));

    }

    @Transactional
    public void deAssignBookFromPerson(int bookId) {
        Optional<Book> book = booksRepository.findById(bookId);

        book.ifPresent(val -> val.setPersonId(null));
    }
}
