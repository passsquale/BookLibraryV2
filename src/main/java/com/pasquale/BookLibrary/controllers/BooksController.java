package com.pasquale.BookLibrary.controllers;


import com.pasquale.BookLibrary.dao.BookDAO;
import com.pasquale.BookLibrary.dao.PersonDAO;
import com.pasquale.BookLibrary.models.Book;
import com.pasquale.BookLibrary.models.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final PersonDAO personDAO;

    private final BookDAO bookDAO;
    @Autowired
    public BooksController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }
    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("bookOwner", bookDAO.showPerson(id));
        model.addAttribute("people", personDAO.index());
        return "books/show";
    }
    @GetMapping("/new")
    public String addBook(@ModelAttribute("book") Book book){
        return "books/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String updateBook(@PathVariable("id") int book_id, Model model){
        model.addAttribute("book", bookDAO.show(book_id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int book_id, @ModelAttribute("book") @Valid Book book,
                       BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        bookDAO.edit(book, book_id);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int book_id){
        bookDAO.delete(book_id);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookDAO.release(id);
        return "redirect:/books/" + id;
    }
    @PatchMapping("/{id}/assign")
    public String asign(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        bookDAO.assign(id, person.getId());
        return "redirect:/books/" + id;
    }
}
