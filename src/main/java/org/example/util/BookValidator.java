package org.example.util;

import org.example.models.book.Book;
import org.example.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {
    private final BooksService booksService;

    @Autowired
    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (!booksService.findByName(book.getName()).isEmpty() &&
            !booksService.findByAuthor(book.getAuthor()).isEmpty())
        {
            errors.rejectValue(
                    "name", "", "A book with the same title and author already exists."
            );
            errors.rejectValue(
                    "author", "", "A book with the same title and author already exists."
            );
        }
    }
}
