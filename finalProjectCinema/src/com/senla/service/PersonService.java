package com.senla.service;

import com.senla.model.Person;

import java.util.List;

public interface PersonService {

    Person create(Person person);

    Person checkPersonForExistenceByLogin(String login, String myPassword);

    String checkPersonInDB(String login);

    boolean checkPersonInDBbyID(int UserID);

    List<Person> getAllPersons();

    Person printUserByID(int userID);

    String getUserMoney(String myLogin);

    void addMoneyWithLogin(String myLogin, int userInputtedMoney);

    int getUserIDbyLogin(String myLogin);

    void updateUserAndAdminMoney(int ticketFinalPrice, String myLogin);

    void updateUserAndAdminMoneyReturned(int ticketPrice, String myLogin);

    boolean checkPersonLoginInDB(String personWantedLogin);

    void deleteUser(int userIdToDelete);
}
