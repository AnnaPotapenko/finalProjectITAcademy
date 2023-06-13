package com.senla.service;

import com.senla.model.Film;

import java.util.List;


public interface FilmService {

    Film create(Film film);

    List<Film> getAllFilm();

    boolean checkFilmID(int filmId);

    List<String> getFilmInfoForTicketUse(int filmID);

    void updateFilmInfo(int filmIdToChange, String changedDataTime, String changedPrice);

    boolean isExpired(int filmId);

    String getFilmDateTime(int FilmId);

    void deleteFilm(int filmIdToDelete);
}


