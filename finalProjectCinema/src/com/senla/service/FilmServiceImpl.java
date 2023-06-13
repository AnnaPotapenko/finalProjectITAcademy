package com.senla.service;

import com.senla.Main;
import com.senla.model.Film;
import com.senla.repository.FilmRepositoryImpl;
import com.senla.repository.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilmServiceImpl implements FilmService {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private final Repository<Film> repository = new FilmRepositoryImpl();
    private final FilmRepositoryImpl filmRepository = new FilmRepositoryImpl();


    @Override
    public Film create(Film film) {

        LOGGER.log(Level.INFO, "Идем в БД для создания фильма");
        return getRepository().create(film);
    }

    @Override
    public List<Film> getAllFilm() {

        LOGGER.log(Level.INFO, "Идем в БД, достаем все фильмы");
        return getFilmRepository().getAllFilm();
    }

    @Override
    public boolean checkFilmID(int filmId) {

        LOGGER.log(Level.INFO, "Идем в БД, проверяем есть ли в ней айди фильма");
        return getFilmRepository().checkFilmByID(filmId);
    }

    @Override
    public List<String> getFilmInfoForTicketUse(int filmID) {

        LOGGER.log(Level.INFO, "Идем в БД, чтобы достать инфо фильма");
        return getFilmRepository().getFilmInfo(filmID);
    }

    @Override
    public void updateFilmInfo(int filmIdToChange, String changedDataTime, String changedPrice) {

        LOGGER.log(Level.INFO, "Идем в БД для изменения данных фильма");
        getFilmRepository().updateFilmInfo(filmIdToChange, changedDataTime, changedPrice);
    }

    @Override
    public boolean isExpired(int filmId) {

        LOGGER.log(Level.INFO, "Начало метода по проверке даты фильма на актуальность");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LOGGER.log(Level.INFO, "Вызов метода по получению даты проведения фильма из БД");
        String filmTime = getFilmDateTime(filmId);
        LocalDateTime filmLocalDateTime = LocalDateTime.parse(filmTime, formatter);
        LocalDateTime now = LocalDateTime.now();
        LOGGER.log(Level.INFO, "Проверка даты проведения фильма на актуальность");
        boolean isBefore = filmLocalDateTime.isBefore(now);
        return isBefore;
    }

    @Override
    public String getFilmDateTime(int filmId) {

        LOGGER.log(Level.INFO, "Идем в БД для получения даты проведения фильма");
        return getFilmRepository().getFilmDateTime(filmId);
    }

    @Override
    public void deleteFilm(int filmIdToDelete) {

        LOGGER.log(Level.INFO, "Идем в БД для удаления фильма");
        getFilmRepository().deleteFilm(filmIdToDelete);
    }

    public Repository<Film> getRepository() {
        return repository;
    }

    public FilmRepositoryImpl getFilmRepository() {
        return filmRepository;
    }
}
