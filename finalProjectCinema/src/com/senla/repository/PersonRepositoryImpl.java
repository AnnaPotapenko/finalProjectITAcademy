package com.senla.repository;

import com.senla.Main;
import com.senla.model.Person;
import com.senla.service.TicketService;
import com.senla.service.TicketServiceImpl;
import com.senla.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonRepositoryImpl implements PersonRepository {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    @Override
    public Person create(Person person) {

        LOGGER.log(Level.INFO, "Начало метода БД по записи пользователя в БД");
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("INSERT INTO my_person (my_person_login, my_person_password) VALUES (?,?)");
            statement.setString(1, person.getPersonLogin());
            statement.setString(2, person.getPersonPassword());
            LOGGER.log(Level.INFO, "Запись данных пользователя в БД");
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public List<Person> getAllPersons() {

        LOGGER.log(Level.INFO, "Начало метода по получению списка пользователей");
        List<Person> personList = new ArrayList<>();
        String personRole = "ORDINARY_USER";
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_person WHERE my_person_role = ?");
            statement.setString(1, personRole);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                LOGGER.log(Level.INFO, "Получение данных пользователей из БД");
                int id = resultSet.getInt("my_person_id");
                String userName = resultSet.getString("my_person_login");
                String password = resultSet.getString("my_person_password");
                String role = resultSet.getString("my_person_role");
                LOGGER.log(Level.INFO, "Запись пользователей в список");
                Person person = new Person(id, userName, password, role);
                personList.add(person);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return personList;
    }

    @Override
    public String getUserAgainForReadingRole(String login) {

        LOGGER.log(Level.INFO, "Начло работы метода БД, который достает роль пользователя");
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.WARNING, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_person WHERE my_person_login = ?");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            String role = null;
            while (resultSet.next()) {
                LOGGER.log(Level.INFO, "Достаем роль пользователя");
                role = resultSet.getString("my_person_role");
            }
            return role;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Person getByLoginAndPassword(String login, String password) {

        LOGGER.log(Level.INFO, "Начло работы метода БД по проверке логина и пароля пользователя");
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подклюение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_person WHERE my_person_login = ? AND my_person_password = ?");
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            Person person = null;
            while (resultSet.next()) {
                LOGGER.log(Level.INFO, "Достаем пользователя из БД");
                int id = resultSet.getInt("my_person_id");
                String userName = resultSet.getString("my_person_login");
                String password2 = resultSet.getString("my_person_password");
                String role = resultSet.getString("my_person_role");
                person = new Person(id, userName, password2, role);
            }
            return person;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkUserByID(int userID) {

        LOGGER.log(Level.INFO, "Начало метода БД по проверке наличия айди пользователя");
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_person WHERE my_person_id = ?");
            statement.setInt(1, userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                LOGGER.log(Level.INFO, "Проверка наличия айди пользователя в бд");
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Person getUserByID(int userID) {

        LOGGER.log(Level.INFO, "Начало метода БД по получению пользователя");
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_person WHERE my_person_id = ?");
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            Person person = null;
            while (resultSet.next()) {
                LOGGER.log(Level.INFO, "Достаем пользователя из БД");
                int id = resultSet.getInt("my_person_id");
                String userName = resultSet.getString("my_person_login");
                String role = resultSet.getString("my_person_role");
                person = new Person(id, userName, role);
            }
            return person;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getMoneySum(String myLogin) {

        LOGGER.log(Level.INFO, "Начало работы метода БД, который достанет актуальный баланс пользователя или кинотеатра");
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.WARNING, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_person WHERE my_person_login = ?");
            statement.setString(1, myLogin);
            ResultSet resultSet = statement.executeQuery();
            String money = null;
            while (resultSet.next()) {
                LOGGER.log(Level.INFO, "Достаем деньги из БД");
                money = resultSet.getString("my_person_money");
            }
            return money;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getUserIDbyLogin(String myLogin) {

        LOGGER.log(Level.INFO, "Начало метода по работе с БД, метод достает айди пользователя по логину");
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_person WHERE my_person_login = ?");

            statement.setString(1, myLogin);
            ResultSet resultSet = statement.executeQuery();
            int personId = 0;
            while (resultSet.next()) {
                LOGGER.log(Level.INFO, "Достаем айди");
                personId = resultSet.getInt("my_person_id");
            }
            return personId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUserAndAdminMoney(int ticketFinalPrice, String myLogin) {

        LOGGER.log(Level.INFO, "Начало метода БД, кторый переведет сумму стоимости билета от пользователя в кинотеатр");
        String adminLogin = "MyAdmin";
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("UPDATE my_person SET my_person_money = my_person_money - ?" +
                            " WHERE my_person_login = ?");
            statement.setInt(1, ticketFinalPrice);
            statement.setString(2, myLogin);
            LOGGER.log(Level.INFO, "Снимаем деньги у пользователя");
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("UPDATE my_person SET my_person_money = my_person_money + ?" +
                            " WHERE my_person_login = ?");
            statement.setInt(1, ticketFinalPrice);
            statement.setString(2, adminLogin);
            LOGGER.log(Level.INFO, "Добавляем денги на баланс кинотеатра");
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUserAndAdminMoneyReturned(int ticketPrice, String myLogin) {

        LOGGER.log(Level.INFO, "Начало метода БД, который переведет деньги за билет пользователю");
        String adminLogin = "MyAdmin";
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("UPDATE my_person SET my_person_money = my_person_money + ?" +
                            " WHERE my_person_login = ?");
            statement.setInt(1, ticketPrice);
            statement.setString(2, myLogin);
            LOGGER.log(Level.INFO, "Добавляем стоимость билета к балансу пользователя");
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("UPDATE my_person SET my_person_money = my_person_money - ?" +
                            " WHERE my_person_login = ?");
            statement.setInt(1, ticketPrice);
            statement.setString(2, adminLogin);
            LOGGER.log(Level.INFO, "Отнимаем от баланса кинотеатра стоимости билета");
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkPersonLoginInDB(String personWantedLogin) {

        LOGGER.log(Level.INFO, "Начало работы метода БД, по проверке логина на занятость");
        boolean isPersonLoginInDB;
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.WARNING, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM my_person WHERE my_person_login = ?");
            statement.setString(1, personWantedLogin);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                LOGGER.log(Level.INFO, "Такой пользователь уже существует.");
                isPersonLoginInDB = true;
            } else {
                LOGGER.log(Level.INFO, "Логин свободен");
                isPersonLoginInDB = false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isPersonLoginInDB;
    }

    @Override
    public void deleteUser(int userIdToDelete) {

        LOGGER.log(Level.INFO, "Начало метода БД по удалению пользователя");
        TicketService ticketService = new TicketServiceImpl();
        LOGGER.log(Level.INFO, "Вызов метода по удалению билетов пользователя");
        ticketService.deleteAllUserTicket(userIdToDelete);
        try (Connection connection = ConnectionManager.open()) {
            LOGGER.log(Level.INFO, "Подключение к БД");
            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM my_person WHERE my_person_id =?");
            statement.setInt(1, userIdToDelete);
            statement.executeUpdate();
            LOGGER.log(Level.INFO, "Удаление пользователя");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void addMoneyByLogin(String myLogin, int userInputtedMoney) {

        LOGGER.log(Level.INFO, "Начало метода БД, который запишет денги на баланс пользователя");
        try {
            int moneyExist = Integer.parseInt(getMoneySum(myLogin));
            int moneyFinalSumOnAccount = moneyExist + userInputtedMoney;
            try (Connection connection = ConnectionManager.open()) {
                LOGGER.log(Level.INFO, "Подключение к БД");
                PreparedStatement statement =
                        connection.prepareStatement("UPDATE my_person SET my_person_money = ? WHERE my_person_login = ? ");
                statement.setInt(1, moneyFinalSumOnAccount);
                statement.setString(2, myLogin);
                statement.execute();
                LOGGER.log(Level.INFO, "Добавляем деньги к балансу пользователя");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Ошибка, добавляем в БД денги, если баланс пользователя изначально null");
            int moneyBalance = 0;
            int moneyFinalSumOnAccount = moneyBalance + userInputtedMoney;
            try (Connection connection = ConnectionManager.open()) {
                LOGGER.log(Level.INFO, "Подключение к БД");
                PreparedStatement statement =
                        connection.prepareStatement("UPDATE my_person SET my_person_money = ? WHERE my_person_login = ? ");
                statement.setInt(1, moneyFinalSumOnAccount);
                statement.setString(2, myLogin);
                statement.execute();
                LOGGER.log(Level.INFO, "Добавление денег к балансу пользователя");
            } catch (SQLException exception) {
                e.printStackTrace();
            }
        }
    }
}
