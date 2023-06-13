package com.senla.repository;

import com.senla.model.Person;

import java.util.List;

public interface PersonRepository extends Repository<Person> {

    List<Person> getAllPersons();

    String getUserAgainForReadingRole(String login);

    Person getByLoginAndPassword(String login, String password);

    boolean checkUserByID(int userID);

    Person getUserByID(int userID);

    String getMoneySum(String myLogin);

    int getUserIDbyLogin(String myLogin);

    void updateUserAndAdminMoney(int ticketFinalPrice, String myLogin);

    void updateUserAndAdminMoneyReturned(int ticketPrice, String myLogin);

    boolean checkPersonLoginInDB(String personWantedLogin);

    void deleteUser(int userIdToDelete);
}
