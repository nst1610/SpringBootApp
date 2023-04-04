package ru.semenova.springcourse.Project2Boot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.semenova.springcourse.Project2Boot.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {


}
