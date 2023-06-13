package com.senla.model;

import java.util.Objects;

public class Person {
    private int personID;
    private String personLogin;
    private String personPassword;
    private String personRole;

    public Person() {
    }

    public Person(int personID, String userName, String password, String role) {
        this.personID = personID;
        this.personLogin = userName;
        this.personPassword = password;
        this.personRole = role;
    }

    public Person(int id, String userName, String role) {
        this.personID = id;
        this.personLogin = userName;
        this.personRole = role;
    }

    public String getPersonLogin() {
        return personLogin;
    }

    public void setPersonLogin(String personLogin) {
        this.personLogin = personLogin;
    }


    public String getPersonPassword() {
        return personPassword;
    }

    public void setPersonPassword(String personPassword) {
        this.personPassword = personPassword;
    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + personID +
                ", userName='" + personLogin + '\'' + ", Role='" + personRole +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personID == person.personID && Objects.equals(personLogin, person.personLogin) && Objects.equals(personPassword, person.personPassword) && Objects.equals(personRole, person.personRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID, personLogin, personPassword, personRole);
    }
}
