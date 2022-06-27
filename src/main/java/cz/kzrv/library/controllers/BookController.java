package cz.kzrv.library.controllers;


import cz.kzrv.library.dao.BookDao;
import cz.kzrv.library.dao.PersonDao;
import cz.kzrv.library.models.Book;
import cz.kzrv.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/books")
@Controller

public class BookController {

    private final BookDao bookDao;
    private final PersonDao personDao;

    @Autowired
    public BookController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books",bookDao.index());
        return "/books/index";
    }
    @GetMapping("{id}")
    public String show(Model model, @PathVariable("id") int id, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookDao.show(id));
        Optional<Person> optionalBook = bookDao.getOwner(id);
        if(optionalBook.isPresent()) model.addAttribute("owner",optionalBook.get());
        else model.addAttribute("people",personDao.index());
        return "books/show";
    }
    @DeleteMapping("{id}")
    public String delete(@PathVariable("id")int id){
        bookDao.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "/books/new";
    }
    @PostMapping()
    public String creatBook(@ModelAttribute("book")@Valid Book book, BindingResult result){
        if(result.hasErrors()) return "/books/new";
        bookDao.save(book);
        return "redirect:/books";
    }

    @PatchMapping("{id}/make")
    public String makeOwner(@ModelAttribute("person") Person person,@PathVariable("id") int id){
        bookDao.makeOwner(person.getId(),id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("{id}/free")
    public String makeFree(@PathVariable("id") int id){
        bookDao.makeFree(id);
        return "redirect:/books/" + id;
    }
    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id")int id,Model model){
        model.addAttribute("book",bookDao.show(id));
        return "books/edit";
    }
    @PatchMapping("{id}")
    public String editPatch(@ModelAttribute("book") @Valid Book book, BindingResult result){
        if(result.hasErrors()){
            return "books/edit";
        } else {
            bookDao.update(book);
            return "redirect:/books";
        }
    }
}
