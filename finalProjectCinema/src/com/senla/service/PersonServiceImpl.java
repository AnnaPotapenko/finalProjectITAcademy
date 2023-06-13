package com.senla.service;

import com.senla.Main;
import com.senla.model.Person;
import com.senla.repository.PersonRepositoryImpl;
import com.senla.repository.Repository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonServiceImpl implements PersonService {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private final Repository<Person> repository = new PersonRepositoryImpl();
    private final PersonRepositoryImpl personRepository = new PersonRepositoryImpl();


    @Override
    public Person create(Person person) {

        LOGGER.log(Level.INFO, "Идем в БД и создаем пользователя");
        return getRepository().create(person);
    }

    @Override
    public Person checkPersonForExistenceByLogin(String login, String myPassword) {

        LOGGER.log(Level.INFO, "Идем в БД для проверки логина и пароля на существование в БД");
        return getPersonRepository().getByLoginAndPassword(login, myPassword);
    }

    @Override
    public String checkPersonInDB(String login) {

        LOGGER.log(Level.INFO, "Идем в БД для получения роли пользователя");
        return getPersonRepository().getUserAgainForReadingRole(login);
    }

    @Override
    public boolean checkPersonInDBbyID(int userID) {

        LOGGER.log(Level.INFO, "Идем в БД, для проверки айди пользователя в БД");
        return getPersonRepository().checkUserByID(userID);
    }

    @Override
    public List<Person> getAllPersons() {

        LOGGER.log(Level.INFO, "Идем в БД для получения списка пользователей");
        return getPersonRepository().getAllPersons();
    }

    @Override
    public Person printUserByID(int userID) {

        LOGGER.log(Level.INFO, "Идем в бд для получения пользователя");
        return getPersonRepository().getUserByID(userID);
    }

    @Override
    public String getUserMoney(String myLogin) {

        LOGGER.log(Level.INFO, "Идем в БД и достаем деньги пользователя или кинотеатра");
        return getPersonRepository().getMoneySum(myLogin);
    }

    @Override
    public void addMoneyWithLogin(String myLogin, int userInputtedMoney) {
        LOGGER.log(Level.INFO, "Идем в БД, передавать деньги на баланс пользователя");
        getPersonRepository().addMoneyByLogin(myLogin, userInputtedMoney);
    }

    @Override
    public int getUserIDbyLogin(String myLogin) {
        LOGGER.log(Level.INFO, "Идем в БД, доставать айди пользователя по его логину");
        return getPersonRepository().getUserIDbyLogin(myLogin);
    }

    @Override
    public void updateUserAndAdminMoney(int ticketFinalPrice, String myLogin) {

        LOGGER.log(Level.INFO, "Идем в БД, перевод денег от пользователя к кинотеатру");
        getPersonRepository().updateUserAndAdminMoney(ticketFinalPrice, myLogin);
    }

    @Override
    public void updateUserAndAdminMoneyReturned(int ticketPrice, String myLogin) {

        LOGGER.log(Level.INFO, "Идем в БД, для перевода денег от админа к пользователю");
        getPersonRepository().updateUserAndAdminMoneyReturned(ticketPrice, myLogin);
    }

    @Override
    public boolean checkPersonLoginInDB(String personWantedLogin) {

        LOGGER.log(Level.INFO, "Идем в БД, где проверим логин на занятость другим пользователем");
        return getPersonRepository().checkPersonLoginInDB(personWantedLogin);
    }

    @Override
    public void deleteUser(int userIdToDelete) {

        LOGGER.log(Level.INFO, "Идем в БД для удаления пользователя");
        getPersonRepository().deleteUser(userIdToDelete);
    }

    public Repository<Person> getRepository() {
        return repository;
    }

    public PersonRepositoryImpl getPersonRepository() {
        return personRepository;
    }
}
