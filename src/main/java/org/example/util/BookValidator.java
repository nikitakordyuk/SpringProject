package org.example.util;

import org.example.dao.book.BookDAO;
import org.example.models.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {
    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (bookDAO.showByName(book.getName()).isPresent() && bookDAO.showByAuthor(book.getAuthor()).isPresent()) {
            errors.rejectValue(
                    "name", "", "A book with the same title and author already exists."
            );
            errors.rejectValue(
                    "author", "", "A book with the same title and author already exists."
            );
        }
    }
}
