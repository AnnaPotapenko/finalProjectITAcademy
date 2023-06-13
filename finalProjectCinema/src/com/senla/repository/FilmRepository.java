package com.senla.repository;

import com.senla.model.Film;

import java.util.List;

public interface FilmRepository extends Repository<Film> {

    List<Film> getAllFilm();

    boolean checkFilmByID(int FilmID);

    List getFilmInfo(int filmID);

    void updateFilmInfo(int filmIdToChange, String changedDataTime, String changedPrice);

    String getFilmDateTime(int filmId);

    void deleteFilm(int filmIdToDelete);
}
