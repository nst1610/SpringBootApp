package ru.semenova.springcourse.Project2Boot.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semenova.springcourse.Project2Boot.models.Book;
import ru.semenova.springcourse.Project2Boot.models.Person;
import ru.semenova.springcourse.Project2Boot.repositories.BooksRepository;
import ru.semenova.springcourse.Project2Boot.repositories.PeopleRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final BooksRepository booksRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BooksRepository booksRepository) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOne(int id){
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public List<Book> getPersonsBook(int id){
        Person person = peopleRepository.findById(id).orElse(null);
        if(person != null){
            Hibernate.initialize(person.getBooks());
            List<Book> listOfPersonBooks = person.getBooks();
            for(Book book : listOfPersonBooks){
                if(((new Date().getTime() - book.getDateOfTaking().getTime()) / (1000 * 60 * 60 * 24))> 10){
                    book.setOverdue(true);
                }
            }
            return person.getBooks();
        } else {
            return Collections.emptyList();
        }
    }
}
