package com.senla.repository;

import com.senla.Main;
import com.senla.model.Film;
import com.senla.service.TicketService;
import com.senla.service.TicketServiceImpl;
import com.senla.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilmRepositoryImpl implements FilmRepository {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    @Override
    public Film create(Film film) {

        LOGGER.log(Level.INFO, "Начало метода БД по созданию фильма");
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("INSERT INTO my_film " +
                            "(my_film_name, my_film_type, my_film_price, my_film_time)" +
                            " VALUES (?,?,?,?)");
            statement.setString(1, film.getFilmName());
            statement.setString(2, film.getFilmType());
            statement.setString(3, film.getFilmPrice());
            statement.setString(4, film.getFilmTime());
            statement.execute();
            LOGGER.log(Level.INFO, "Создание фильма");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return film;
    }

    @Override
    public List<Film> getAllFilm() {

        LOGGER.log(Level.INFO, "Начало метода БД, который достает все фильмы");
        List<Film> filmList = new ArrayList<>();

        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_film");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                LOGGER.log(Level.INFO, "Получение инфо фильма из БД");
                int id = resultSet.getInt("my_film_id");
                String filmName = resultSet.getString("my_film_name");
                String filmType = resultSet.getString("my_film_type");
                String filmPrice = resultSet.getString("my_film_price");
                String filmTime = resultSet.getString("my_film_time");
                LOGGER.log(Level.INFO, "Запись фильмов в список");
                Film film = new Film(id, filmName, filmType, filmPrice, filmTime);
                filmList.add(film);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filmList;
    }

    @Override
    public boolean checkFilmByID(int filmID) {

        LOGGER.log(Level.INFO, "Начало метода БД по проверке айди фильма на наличие в БД");
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_film WHERE my_film_id = ?");
            statement.setInt(1, filmID);

            try (ResultSet resultSet = statement.executeQuery()) {
                LOGGER.log(Level.INFO, "Проверям айди фильма в БД");
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> getFilmInfo(int filmID) {

        LOGGER.log(Level.INFO, "Начало метода БД, который достает из БД фильм инфо: имя, цену, время");
        List<String> filmListInfo = new ArrayList<>();

        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_film WHERE my_film_id = ?");
            statement.setInt(1, filmID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LOGGER.log(Level.INFO, "Достаем инфо фильма из БД");
                String filmName = resultSet.getString("my_film_name");
                String filmPrice = resultSet.getString("my_film_price");
                String filmTime = resultSet.getString("my_film_time");
                LOGGER.log(Level.INFO, "Запись инфо в список");
                filmListInfo.add(0, filmName);
                filmListInfo.add(1, filmPrice);
                filmListInfo.add(2, filmTime);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filmListInfo;
    }

    @Override
    public void updateFilmInfo(int filmIdToChange, String changedDataTime, String changedPrice) {

        LOGGER.log(Level.INFO, "Начало метода БД по изменению данных фильма");
        TicketService ticketService = new TicketServiceImpl();
        LOGGER.log(Level.INFO, "Вызов метода по удалению билетов фильма");
        ticketService.deleteAllTicket(filmIdToChange);

        if (changedDataTime == null) {
            LOGGER.log(Level.INFO, "Вызов блока по изменению фильма если не нужно изменять дату");
            try (Connection connection = ConnectionManager.open()) {
                LOGGER.log(Level.INFO, "Подключение к БД");
                PreparedStatement statement =
                        connection.prepareStatement("UPDATE my_film SET my_film_price =?" +
                                " WHERE my_film_id = ?");
                statement.setString(1, changedPrice);
                statement.setInt(2, filmIdToChange);
                statement.executeUpdate();
                LOGGER.log(Level.INFO, "Внесение изменений");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (changedPrice == null) {
            LOGGER.log(Level.INFO, "Вызов блока по изменению фильма если не нужно изменять стоимость");
            try (Connection connection = ConnectionManager.open()) {
                LOGGER.log(Level.INFO, "Подключение к БД");
                PreparedStatement statement =
                        connection.prepareStatement("UPDATE my_film SET my_film_time =?" +
                                " WHERE my_film_id = ?");
                statement.setString(1, changedDataTime);
                statement.setInt(2, filmIdToChange);
                statement.executeUpdate();
                LOGGER.log(Level.INFO, "Внесение изменений");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            LOGGER.log(Level.INFO, "Вызов блока по изменению фильма если изменяем дату и цену");
            try (Connection connection = ConnectionManager.open()) {
                LOGGER.log(Level.INFO, "Подключение к БД");
                PreparedStatement statement =
                        connection.prepareStatement("UPDATE my_film SET my_film_time =?, my_film_price =?" +
                                " WHERE my_film_id = ?");
                statement.setString(1, changedDataTime);
                statement.setString(2, changedPrice);
                statement.setInt(3, filmIdToChange);
                statement.executeUpdate();
                LOGGER.log(Level.INFO, "Внесение изменений");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String getFilmDateTime(int filmId) {

        LOGGER.log(Level.INFO, "Начало метода БД по получению даты проведения фильма");
        String filmTime = null;

        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_film WHERE my_film_id = ?");
            statement.setInt(1, filmId);
            ResultSet resultSet = statement.executeQuery();
            LOGGER.log(Level.INFO, "Получение даты проведения фильма из БД");
            while (resultSet.next()) {
                filmTime = resultSet.getString("my_film_time");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filmTime;
    }

    @Override
    public void deleteFilm(int filmIdToDelete) {

        LOGGER.log(Level.INFO, "Начало метода БД по удалению фильма");
        TicketService ticketService = new TicketServiceImpl();
        LOGGER.log(Level.INFO, "Вызов метода по удалению билетов фильма");
        ticketService.deleteAllTicket(filmIdToDelete);
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM my_film WHERE my_film_id =?");
            statement.setInt(1, filmIdToDelete);
            LOGGER.log(Level.INFO, "Удаление фильма из БД");
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
