package com.senla.repository;

import com.senla.Main;
import com.senla.model.Ticket;
import com.senla.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketRepositoryImpl implements TicketRepository {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    @Override
    public Ticket createTicketWithFilmID(Ticket ticket, int filmID) {

        LOGGER.log(Level.INFO, "Начало метода БД по созданию билетов фильма");
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("INSERT INTO my_ticket" +
                            "(my_ticket_film_name, my_film_price, my_film_time, my_film)" +
                            " VALUES (?,?,?,?)");
            statement.setString(1, ticket.getTicketFilmName());
            statement.setString(2, ticket.getTicketPrice());
            statement.setString(3, ticket.getTicketFilmTime());
            statement.setInt(4, filmID);
            LOGGER.log(Level.INFO, "Запись билета в БД");
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    @Override
    public List<Ticket> getAllTicket() {

        LOGGER.log(Level.INFO, "Начало метода БД по получению списка билетов");
        List<Ticket> ticketList = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_ticket");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                LOGGER.log(Level.INFO, "Получение инфы о билетах из БД");
                int id = resultSet.getInt("my_ticket_id");
                String ticketFilmName = resultSet.getString("my_ticket_film_name");
                String ticketStatus = resultSet.getString("my_ticket_status");
                String ticketPrice = resultSet.getString("my_film_price");
                String ticketTime = resultSet.getString("my_film_time");
                LOGGER.log(Level.INFO, "Запись билетов в список");
                Ticket ticket = new Ticket(id, ticketFilmName, ticketStatus, ticketPrice, ticketTime);
                ticketList.add(ticket);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketList;
    }

    @Override
    public List<Ticket> getAllTicketUsingFilmID(int filmID) {

        LOGGER.log(Level.INFO, "Начало метода БД по получению списка билетов фильма");
        List<Ticket> ticketList = new ArrayList<>();

        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_ticket WHERE my_film  = ?");
            statement.setInt(1, filmID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                LOGGER.log(Level.INFO, "Достаем инфо билетов");
                int id = resultSet.getInt("my_ticket_id");
                String ticketFilmName = resultSet.getString("my_ticket_film_name");
                String ticketStatus = resultSet.getString("my_ticket_status");
                String ticketPrice = resultSet.getString("my_film_price");
                String ticketTime = resultSet.getString("my_film_time");
                LOGGER.log(Level.INFO, "Записываем билеты в список");
                Ticket ticket = new Ticket(id, ticketFilmName, ticketStatus, ticketPrice, ticketTime);
                ticketList.add(ticket);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketList;
    }

    @Override
    public List<Ticket> getNotSoldTicketList(int filmID) {

        LOGGER.log(Level.INFO, "Работа в БД, достаем доступные к продаже билеты");
        List<Ticket> ticketList = new ArrayList<>();
        String ticketStatus = "NOT_SOLD";
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_ticket WHERE my_film  = ? AND my_ticket_status = ?");
            statement.setInt(1, filmID);
            statement.setString(2, ticketStatus);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("my_ticket_id");
                String ticketFilmName = resultSet.getString("my_ticket_film_name");
                String ticketStatusInDB = resultSet.getString("my_ticket_status");
                String ticketPrice = resultSet.getString("my_film_price");
                String ticketTime = resultSet.getString("my_film_time");
                Ticket ticket = new Ticket(id, ticketFilmName, ticketStatusInDB, ticketPrice, ticketTime);
                ticketList.add(ticket);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketList;
    }

    @Override
    public void addUserIDtoTicket(int userID, int filmId) {

        LOGGER.log(Level.INFO, "Начало метода БД, который добавиит айди пользователя к билету и пометит как SOLD ");
        String ticketStatusNotSold = "NOT_SOLD";
        String ticketStatusSold = "SOLD";
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("UPDATE my_ticket SET my_person =?, my_ticket_status =?" +
                            " WHERE my_film  = ? AND my_ticket_status = ? LIMIT 1");
            statement.setInt(1, userID);
            statement.setString(2, ticketStatusSold);
            statement.setInt(3, filmId);
            statement.setString(4, ticketStatusNotSold);
            LOGGER.log(Level.INFO, "Внесение изменений в билет");
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ticket> getTicketWithUserID(int userID) {

        LOGGER.log(Level.INFO, "Начало метода БД по получению списка билетов, используя айди пользователя");
        List<Ticket> ticketList = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_ticket WHERE my_person = ?");
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LOGGER.log(Level.INFO, "Получаем из БД данные билета");
                int id = resultSet.getInt("my_ticket_id");
                String ticketFilmName = resultSet.getString("my_ticket_film_name");
                String ticketStatusInDB = resultSet.getString("my_ticket_status");
                String ticketPrice = resultSet.getString("my_film_price");
                String ticketTime = resultSet.getString("my_film_time");
                Ticket ticket = new Ticket(id, ticketFilmName, ticketStatusInDB, ticketPrice, ticketTime);
                LOGGER.log(Level.INFO, "Записываем данные билета в список");
                ticketList.add(ticket);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketList;
    }

    @Override
    public List<Integer> getTicketIDListWithUserLogin(String myLogin) {

        LOGGER.log(Level.INFO, "Работа с БД, метод должен достать список айди билетов конкретного пользователя");
        List<Integer> ticketList = new ArrayList<>();
        int userID = 0;
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_person WHERE my_person_login = ?");
            statement.setString(1, myLogin);
            ResultSet resultSet = statement.executeQuery();
            LOGGER.log(Level.INFO, "Достаем айди пользователя по его логину");
            while (resultSet.next()) {
                userID = resultSet.getInt("my_person_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_ticket WHERE my_person = ?");
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            LOGGER.log(Level.INFO, "Достаем список билетов по айди пользователя");
            while (resultSet.next()) {
                int id = resultSet.getInt("my_ticket_id");
                ticketList.add(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketList;

    }

    @Override
    public int getTicketPrice(int userInputtedTicketID) {

        LOGGER.log(Level.INFO, "Начало метода БД, который достанет цену билета");
        int ticketPrice = 0;
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_ticket WHERE my_ticket_id = ?");
            statement.setInt(1, userInputtedTicketID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LOGGER.log(Level.INFO, "Достаем цену билета");
                ticketPrice = Integer.parseInt(resultSet.getString("my_film_price"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketPrice;
    }

    @Override
    public void updateReturnedTicketInfo(int userInputtedTicketID) {

        LOGGER.log(Level.INFO, "Начало метода по изменению данных билета, который вернул пользователь");
        String ticketStatusNotSold = "NOT_SOLD";
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("UPDATE my_ticket SET my_person =?, my_ticket_status =?" +
                            " WHERE my_ticket_id = ?");
            statement.setNull(1, Types.NULL);
            statement.setString(2, ticketStatusNotSold);
            statement.setInt(3, userInputtedTicketID);
            LOGGER.log(Level.INFO, "Изменение данных билета");
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAllTicket(int filmIdToDelete) {

        LOGGER.log(Level.INFO, "Начало метода БД по удалению билетов фильма");
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM my_ticket WHERE my_film =?");
            statement.setInt(1, filmIdToDelete);
            statement.executeUpdate();
            LOGGER.log(Level.INFO, "Удаление билетов фильма из БД");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAllUserTicket(int userIdToDelete) {

        LOGGER.log(Level.INFO, "Начало метода БД по удалению билета пользователя");
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к бд");
            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM my_ticket WHERE my_person =?");
            statement.setInt(1, userIdToDelete);
            statement.executeUpdate();
            LOGGER.log(Level.INFO, "Удаление билета из БД");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getTicketDataTime(int userInputtedTicketID) {

        LOGGER.log(Level.INFO, "Начало метода БД, который достанет дату-время билета");
        String ticketDataTimeFromDB = null;
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.WARNING, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_ticket WHERE my_ticket_id = ?");
            statement.setInt(1, userInputtedTicketID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LOGGER.log(Level.INFO, "Достаем дату-время");
                ticketDataTimeFromDB = resultSet.getString("my_film_time");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ticketDataTimeFromDB;
    }


    @Override
    public Ticket create(Ticket ticket) {
        return null;
    }


}
