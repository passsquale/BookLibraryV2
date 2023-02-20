package com.pasquale.BookLibrary.controllers;

import com.pasquale.BookLibrary.dao.PersonDAO;
import com.pasquale.BookLibrary.models.Person;
import com.pasquale.BookLibrary.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonValidator personValidator;
    private final PersonDAO personDAO;
    @Autowired
    public PeopleController(PersonValidator personValidator, PersonDAO personDAO) {
        this.personValidator = personValidator;
        this.personDAO = personDAO;
    }
    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int person_id, Model model){
        model.addAttribute("person", personDAO.show(person_id));
        model.addAttribute("books", personDAO.getBooks(person_id));
        return"people/show";
    }
    @GetMapping("/new")
    public String addPerson(@ModelAttribute("person") Person person){
        return "people/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            return "people/new";
        }
        personDAO.save(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String updatePerson(Model model, @PathVariable("id") int person_id){
        model.addAttribute("person", personDAO.show(person_id));
        return "people/edit";
    }
    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,@PathVariable("id") int person_id){
        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        personDAO.edit(person, person_id);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int person_id){
        personDAO.delete(person_id);
        return "redirect:/people";
    }
}
