package org.example.controllers;

import org.example.dao.book.BookDAO;
import org.example.dao.person.PersonDAO;
import org.example.models.book.Book;
import org.example.models.person.Person;
import org.example.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;

    private final PersonDAO personDAO;

    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String getIndex(Model model) {
        model.addAttribute("books", bookDAO.index());
        System.out.println(bookDAO.index());

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("people", personDAO.index());

        Optional<Person> personFullName = personDAO.index()
                .stream()
                .filter(x -> bookDAO.show(id).getPersonId() != null)
                .filter(x -> x.getId() == bookDAO.show(id).getPersonId())
                .findAny();

        personFullName.ifPresent(value -> model.addAttribute("personFullName", value.getFullName()));

        System.out.println(personFullName);


        System.out.println(model);

        return "books/show";
    }

    @PatchMapping("/{id}/assign-book")
    public String assignBook(@PathVariable("id") int bookId, @ModelAttribute("person") Person person) {

        bookDAO.assignBookToPerson(person.getId(), bookId);

        return "redirect:/books";
    }

    @PatchMapping("/{id}/deassign-book")
    public String deAssignBook(@PathVariable("id") int bookId) {

        bookDAO.deAssignBookFromPerson(null, bookId);

        return "redirect:/books";
    }

    @GetMapping("/new")
    public String addBook(@ModelAttribute("book") Book book) {

        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) return "books/new";

        bookDAO.save(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));

        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "books/edit";

        bookDAO.update(id, book);

        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);

        return "redirect:/books";
    }

}
