package com.senla.model;

import java.util.Objects;

public class Film {
    private int filmID;
    private String filmName;
    private String filmType;
    private String filmPrice;
    private String filmTime;

    public Film() {
    }

    public Film(int id, String filmName, String filmType, String filmPrice, String filmTime) {
        this.filmID = id;
        this.filmName = filmName;
        this.filmType = filmType;
        this.filmPrice = filmPrice;
        this.filmTime = filmTime;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmType() {
        return filmType;
    }

    public void setFilmType(String filmType) {
        this.filmType = filmType;
    }

    public String getFilmPrice() {
        return filmPrice;
    }

    public void setFilmPrice(String filmPrice) {
        this.filmPrice = filmPrice;
    }

    public String getFilmTime() {
        return filmTime;
    }

    public void setFilmTime(String filmTime) {
        this.filmTime = filmTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return filmID == film.filmID && Objects.equals(filmName, film.filmName) && Objects.equals(filmType, film.filmType) && Objects.equals(filmPrice, film.filmPrice) && Objects.equals(filmTime, film.filmTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmID, filmName, filmType, filmPrice, filmTime);
    }

    @Override
    public String toString() {
        return "Фильм айди: " + filmID +
                ", " + filmName + ". Жанр: " + filmType +
                ". Стоимость: " + filmPrice +
                " рублей. Начало сеанса: " + filmTime;
    }


}
