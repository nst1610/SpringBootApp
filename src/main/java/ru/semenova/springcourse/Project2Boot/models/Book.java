package ru.semenova.springcourse.Project2Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    @NotEmpty(message = "Данное поле не должно быть путсым.")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "Данное поле не должно быть путсым.")
    private String author;

    @Column(name = "year_of_production")
    @Min(value = 1800, message = "Введите корректный год.")
    private int yearOfProduction;

    @ManyToOne()
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person reader;


    @Column(name = "date_of_taking")
    private Date dateOfTaking;


    @Transient
    private Boolean isOverdue;

    public Book(){

    }

    public Book(int id, String title, String author, int yearOfProduction, Person reader) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearOfProduction = yearOfProduction;
        this.reader =reader;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public Person getReader() {
        return reader;
    }

    public void setReader(Person reader) {
        this.reader = reader;
        this.dateOfTaking = new Date();
    }

    public Date getDateOfTaking() {
        return dateOfTaking;
    }

    public void setDateOfTaking(Date dateOfTaking) {
        this.dateOfTaking = dateOfTaking;
    }

    public Boolean getOverdue() {
        return isOverdue;
    }

    public void setOverdue(Boolean overdue) {
        isOverdue = overdue;
    }
}
