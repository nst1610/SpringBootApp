package ru.semenova.springcourse.Project2Boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semenova.springcourse.Project2Boot.models.Book;
import ru.semenova.springcourse.Project2Boot.models.Person;
import ru.semenova.springcourse.Project2Boot.repositories.BooksRepository;
import ru.semenova.springcourse.Project2Boot.repositories.PeopleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public List<Book> findAllOnPage(int page, int booksPerPage){
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public List<Book> findAllSorted(){
        return booksRepository.findAll(Sort.by("yearOfProduction"));
    }

    public List<Book> findAllOnPageAndSort(int page, int booksPerPage){
        return  booksRepository.findAll(PageRequest.of(page, booksPerPage,
                Sort.by("yearOfProduction"))).getContent();
    }

    public Book findOne(int id){
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    public Person getReader(int id){
        return booksRepository.findById(id).orElse(null).getReader();
    }

    @Transactional
    public void freeBook(int id){
        Book book = booksRepository.findById(id).orElse(null);
        if(book != null){
            book.setReader(null);
            book.setOverdue(false);
            booksRepository.save(book);
        }
    }

    @Transactional
    public void selectReader(int bookId, Person person){
        Book book = booksRepository.findById(bookId).orElse(null);
        if (book != null){
            book.setReader(person);
            if (person.getBooks() == null)
                person.setBooks(new ArrayList<>());
            person.getBooks().add(book);
        }
    }

    public List<Book> findByTitleStartingWith(String startingString){
        return booksRepository.findByTitleStartingWith(startingString);
    }
}
