package ru.kasatchikov.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kasatchikov.library.dao.BookDAO;
import ru.kasatchikov.library.dao.PersonDAO;
import ru.kasatchikov.library.models.Book;
import ru.kasatchikov.library.models.Person;
import ru.kasatchikov.library.util.BookValidator;

import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {
    private BookDAO bookDAO;
    private PersonDAO personDAO;
    private BookValidator bookValidator;
    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "/book/index";
    }
    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookDAO.show(id));
        Optional<Person> bookOwner = bookDAO.getBookOwner(id);

        if(bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", personDAO.index());

        return "/book/show";
    }
    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "/book/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors())
            return "/book/new";
        bookDAO.save(book);
        return "redirect:/book";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDAO.show(id));
        return "/book/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors())
            return "/book/edit";
        bookDAO.update(id, book);
        return "redirect:/book";
    }
    @DeleteMapping("/{id}/edit")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/book";
    }
    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id){
        bookDAO.releaseBook(id);
        return "redirect:/book/" + id;
    }
    @PatchMapping("/{id}/assign")
    public String assign(@ModelAttribute("person") Person selectedPerson, @PathVariable("id") int id){
        bookDAO.assignBook(id, selectedPerson);
        return "redirect:/book/" + id;
    }
}