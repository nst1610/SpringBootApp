package ru.semenova.springcourse.Project2Boot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.semenova.springcourse.Project2Boot.models.Book;
import ru.semenova.springcourse.Project2Boot.models.Person;
import ru.semenova.springcourse.Project2Boot.services.BooksService;
import ru.semenova.springcourse.Project2Boot.services.PeopleService;


@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {

        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(name = "page", required = false) String page,
                        @RequestParam(name = "books_per_page", required = false) String booksPerPage,
                        @RequestParam(name = "sort_by_year", required = false) boolean isSort) {
        if(page == null || booksPerPage == null){
            if(isSort) model.addAttribute("books", booksService.findAllSorted());
            else model.addAttribute("books", booksService.findAll());
        }
        else if (isSort == true)
            model.addAttribute("books",
                    booksService.findAllOnPageAndSort(Integer.parseInt(page), Integer.parseInt(booksPerPage)));
        else
            model.addAttribute("books",
                booksService.findAllOnPage(Integer.parseInt(page), Integer.parseInt(booksPerPage)));

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", booksService.findOne(id));
        if (booksService.getReader(id) != null)
            model.addAttribute("reader", booksService.getReader(id));
        else
            model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){

        if (bindingResult.hasErrors())
            return "books/new";

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id){

        if(bindingResult.hasErrors())
            return "books/edit";

        booksService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/free")
    public String freeBook(@PathVariable("id") int id){

        booksService.freeBook(id);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/select")
    public String selectReader(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        booksService.selectReader(id, person);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam(required = false, defaultValue = "") String startString,
                             Model model) {
        model.addAttribute("startString", startString);
        if (!startString.isEmpty()) {
            model.addAttribute("books", booksService.findByTitleStartingWith(startString));
        }
        return "books/search";
    }

}
