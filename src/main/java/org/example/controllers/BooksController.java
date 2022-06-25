package org.example.controllers;

import org.example.models.book.Book;
import org.example.models.person.Person;
import org.example.services.BooksService;
import org.example.services.PeopleService;
import org.example.util.BookValidator;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;

    private final PeopleService peopleService;

    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService, BookValidator bookValidator) {
        this.booksService = booksService;
        this.peopleService = peopleService;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String getIndex(Model model,
                           @RequestParam(value = "page", required = false) Optional<Integer> page,
                           @RequestParam(value = "books_per_page", required = false) Optional<Integer> booksPerPage,
                           @RequestParam(value = "sort_by_year", required = false, defaultValue = "false")
                           Optional<Boolean> sortByYear) {
        List<Book> booksList = booksService.booksList();


        if (page.isPresent() && booksPerPage.isPresent()) {
            if (page.get() >= 0 && booksPerPage.get() > 0)
                booksList = booksService.certainAmount(booksList, booksPerPage.get(), page.get());
        }

        if (sortByYear.isPresent() && sortByYear.get()) booksList = booksService.sort(booksList);

        model.addAttribute("books", booksList);

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.findBook(id));
        model.addAttribute("people", peopleService.peopleList());

        Optional<Person> personFullName = peopleService.peopleList()
                .stream()
                .filter(x -> booksService.findBook(id).getPersonId() != null)
                .filter(x -> x.getId() == booksService.findBook(id).getPersonId())
                .findAny();

        personFullName.ifPresent(value -> model.addAttribute("personFullName", value.getFullName()));

        System.out.println(personFullName);

        System.out.println(model);

        return "books/show";
    }

    @PatchMapping("/{id}/assign-book")
    public String assignBook(@PathVariable("id") int bookId, @ModelAttribute("person") Person person) {

        booksService.assignBookToPerson(person.getId(), bookId);

        return "redirect:/books";
    }

    @PatchMapping("/{id}/deassign-book")
    public String deAssignBook(@PathVariable("id") int bookId) {

        booksService.deAssignBookFromPerson(bookId);

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

        booksService.save(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findBook(id));

        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "books/edit";

        booksService.update(id, book);

        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);

        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(Model model, @ModelAttribute(name = "book") Book book) {
        model.addAttribute("books", booksService.booksList());

        return "books/search/search";
    }

    @PostMapping("/search")
    public String search(@RequestParam("search") String query, Model model)
    {
        List<Book> searchResult = booksService.search(query);
        System.out.println(searchResult);
        model.addAttribute("searchResult", searchResult);


        return "books/search/search";
    }
}
