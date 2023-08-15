package ch.albin.ictskills.controller;


import ch.albin.ictskills.db.DAO;
import ch.albin.ictskills.model.Person;

public abstract class Controller {
    protected static final DAO<Person> PERSON_DAO = new DAO<>(Person.class);
    public abstract void init();
}