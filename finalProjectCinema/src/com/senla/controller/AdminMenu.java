package com.senla.controller;

import com.senla.Main;
import com.senla.model.Film;
import com.senla.model.Person;
import com.senla.model.Ticket;
import com.senla.service.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminMenu extends MainCinemaMenu {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private final FilmService filmService = new FilmServiceImpl();
    private final TicketService ticketService = new TicketServiceImpl();
    private final PersonService personService = new PersonServiceImpl();

    public FilmService getFilmService() {
        return filmService;
    }

    public TicketService getTicketService() {
        return ticketService;
    }

    public PersonService getPersonService() {
        return personService;
    }


    @Override
    public void startMenu() {

        LOGGER.log(Level.INFO, "Начало главного меню админа");
        System.out.println("Добро пожаловать в Меню админа");
        String[] userMenuText = {
                " Введите - 1 - для просмотра доступных фильмов",
                " Введите - 2 - для добавить фильм",
                " Введите - 3 - для удалить фильм",
                " Введите - 4 - для редактирования фильма",
                " Введите - 5 - для добавление билета к фильму",
                " Введите - 6 - для просмотра всех билетов",
                " Введите - 7 - для просмотра всех билетов конкретного фильма",
                " Введите - 8 - для просмотра всех зарегистрированных пользователей",
                " Введите - 9 - для просмотра конкретного пользователя",
                " Введите - 10 - для удалить пользователя по логину",
                " Введите - 11 - для просмотра балланса кинотеатра",
                " Введите - 12 - для выхода из приложения "};
        int option = 1;

        do {
            LOGGER.log(Level.INFO, "Вызов метода по выводу в консоль текста меню");
            printMenu(userMenuText);
            LOGGER.log(Level.INFO, "Вызов метода по проверке и получению введенного пункта меню");
            option = checkInputtedNumber(countMenuLengthFromMenuArray(userMenuText), userMenuText);

            switch (option) {
                case 1 -> {
                    LOGGER.log(Level.INFO, "Вызов метода вывода в консоль фильмов");
                    System.out.println("Просмотр доступных фильмов...");
                    printAllFilm();
                }
                case 2 -> {
                    LOGGER.log(Level.INFO, "Вызов метода создания фильма");
                    System.out.println("Добавить фильм...");
                    createFilm();
                }
                case 3 -> {
                    LOGGER.log(Level.INFO, "Вызов метода удаления фильма");
                    System.out.println("Удалить фильм...");
                    deleteFilm();
                }
                case 4 -> {
                    LOGGER.log(Level.INFO, "Вызов метода для редактирования фильма");
                    System.out.println("Редактироавть фильм...");
                    changeFilmInfo();
                }
                case 5 -> {
                    LOGGER.log(Level.INFO, "Вызов метода для добавления билета к фильму");
                    System.out.println("Добавление билета к фильму...");
                    addTicketToFilm();
                }
                case 6 -> {
                    LOGGER.log(Level.INFO, "Вызов метода вывода билетов в консоль");
                    System.out.println("Просмотр всех билетов...");
                    printAllTicket();
                }
                case 7 -> {
                    LOGGER.log(Level.INFO, "Вызов метода по выводу в консоль билетов фильма");
                    System.out.println("Просмотр всех билетов конкретного фильма...");
                    printTicketUsingFilmID();
                }
                case 8 -> {
                    LOGGER.log(Level.INFO, "Вызов метода для вывода всех пользователей в консоль");
                    System.out.println("Просмотр всех пользователей...");
                    printAllUser();
                }
                case 9 -> {
                    LOGGER.log(Level.INFO, "Вызов метода для вывода пользователя в консоль");
                    System.out.println("Просмотр конкретного пользователя...");
                    printUserByID();
                }
                case 10 -> {
                    LOGGER.log(Level.INFO, "Вызов метода для удаления пользователя");
                    System.out.println("Удалить пользователя по логину...");
                    deleteUserByID();
                }
                case 11 -> {
                    LOGGER.log(Level.INFO, "Вызов метода для вывода баланса кинотеатра в консоль");
                    System.out.println("Просмотр балланса кинотеатра...");
                    printCinemaMoney();
                }
                case 12 -> {
                    LOGGER.log(Level.INFO, "Приложение закончило работу");
                    System.out.println("Приложение закончило работу.");
                    System.exit(0);
                }
            }
        } while (true);
    }

    public int checkFilmID() {

        LOGGER.log(Level.INFO, "Начало метода по проверке айди введенного фильма");
        int inputtedFilmID = 0;
        Scanner scanner = new Scanner(System.in);
        boolean isFilmInJDBC = false;
        System.out.println("Введите айди фильма...");
        while (!isFilmInJDBC) {
            int count = 0;
            do {
                try {
                    LOGGER.log(Level.WARNING, "Пользователь вводит айди фильма");
                    inputtedFilmID = scanner.nextInt();
                    LOGGER.log(Level.INFO, "Вызов метода по проверке введенного айди фильма на наличие в БД");
                    isFilmInJDBC = getFilmService().checkFilmID(inputtedFilmID);

                    if (!isFilmInJDBC) {
                        LOGGER.log(Level.WARNING, "Айди введено неверно");
                        System.out.println("Такого фильма не существует! Введите корректное айди.");
                        count++;

                        if (count == 1) {
                            String[] optionText = {
                                    "Введите - 1 - если да.",
                                    "Введите - 2 - если нет."};
                            System.out.println("Хотите посмотреть список доступных фильмов?");
                            LOGGER.log(Level.INFO, "Вызов метода по выводу текста мини-меню в консоль");
                            super.printMenu(optionText);
                            LOGGER.log(Level.INFO, "Вызов метода по проверке введенного пункта мини-меню");
                            int choice = super.checkInputtedNumber(2, optionText);

                            if (choice == 1) {
                                LOGGER.log(Level.INFO, "Вызов метода по выводу всех фильмов в консоль");
                                printAllFilm();
                                System.out.println("Введите айди фильма...");
                            } else {
                                LOGGER.log(Level.INFO, "Повторный ввод");
                                System.out.println("Введите айди...");
                                break;
                            }
                            break;
                        }
                        break;
                    }
                    break;
                } catch (InputMismatchException e) {
                    LOGGER.log(Level.WARNING, "Введено не число");
                    System.out.println("Вводить необходимо только цифры! \nПопробуйте ещё раз.");
                    System.out.println("Введите ID фильма...");
                    LOGGER.log(Level.INFO, "Повторный ввод");
                    scanner.nextLine();
                }
            } while (true);
        }
        return inputtedFilmID;
    }

    private void printCinemaMoney() {

        LOGGER.log(Level.INFO, "Начало метода по выводу текущего баланса кинотеатра в консоль");
        String adminLogin = "MyAdmin";
        LOGGER.log(Level.INFO, "Вызов метода, котрый получет баланс кинотеатра");
        String cinemaMoney = getPersonService().getUserMoney(adminLogin);
        LOGGER.log(Level.INFO, "Выод баланса кинотеатра в консоль");
        System.out.println("Баланс кинотеатра составляет " + cinemaMoney + " рублей.");
    }

    private void deleteUserByID() {

        LOGGER.log(Level.INFO, "Начало метода по удалению пользователя");
        System.out.println("Введите айди юзера, который хотите удалить...");
        LOGGER.log(Level.INFO, "Вызов метода, который получит и проверит введеный айди пользователя");
        int userIdToDelete = checkUserIDinJDBC();
        LOGGER.log(Level.INFO, "Вызов метода по удалению пользователя из БД");
        getPersonService().deleteUser(userIdToDelete);
        System.out.println("Пользователь удален.");
    }

    private void deleteFilm() {

        LOGGER.log(Level.INFO, "Начало метода по удалению фильма");
        System.out.println("Введите айди фильма, который хотите удалить...");
        LOGGER.log(Level.INFO, "Вызов метода по получению и проверке айди фильма");
        int filmIdToDelete = checkFilmID();
        LOGGER.log(Level.INFO, "Вызов метода по проверке даты фильма на актуальность");
        boolean isExpired = getFilmService().isExpired(filmIdToDelete);

        if (isExpired == true) {
            LOGGER.log(Level.INFO, "Дата фильма прошла. Удаление разрешено");
            LOGGER.log(Level.INFO, "Вызов метода по удалению фильма из БД");
            getFilmService().deleteFilm(filmIdToDelete);
            System.out.println("Фильм удален.");
        } else {
            LOGGER.log(Level.INFO, "Дата фильма еще действительна. Удаление запрещено");
            System.out.println("Дата фильма еще действительна! Фильм изменять нельзя!!!");
            LOGGER.log(Level.INFO, "Вызов меню");
            startMenu();
        }
    }

    private void changeFilmInfo() {

        LOGGER.log(Level.INFO, "Начало метода по изменению фильма");
        System.out.println("Введите айди фильма, который хотите изменить...");
        LOGGER.log(Level.INFO, "Вызов метода по проверке и получению айди фильма");
        int filmIdToChange = checkFilmID();
        String changedDataTime = null;
        String changedPrice = null;
        LOGGER.log(Level.INFO, "Вызов метода по проверке даты изменяемого фильма на действительность");
        boolean isExpired = getFilmService().isExpired(filmIdToChange);

        if (isExpired == true) {
            LOGGER.log(Level.INFO, "Вызов блока, если дата изменяемого фильма просрочена");
            System.out.println("Хотите изменить дату и время?");
            Scanner scanner = new Scanner(System.in);
            String[] optionText = {
                    "Введите - 1 - если да.",
                    "Введите - 2 - если нет."};
            LOGGER.log(Level.INFO, "Вызов метода по выводу текста меню в консоль");
            printMenu(optionText);
            LOGGER.log(Level.INFO, "Вызов метода по проверке введенного пункта меню");
            int userOption = checkInputtedNumber(2, optionText);

            if (userOption == 1) {
                LOGGER.log(Level.INFO, "Начало блока, если вносим изменения в дату фильма");
                System.out.println("Введите желаемые дату и время...");
                LOGGER.log(Level.WARNING, "Ввод новой даты");
                changedDataTime = scanner.nextLine();
                System.out.println("Хотите изменить стоимость?");
                LOGGER.log(Level.INFO, "Вызов метода по выводу мею в консоль");
                printMenu(optionText);
                LOGGER.log(Level.INFO, "Проверка введенного пункта меню");
                int userOption3 = checkInputtedNumber(2, optionText);
                if (userOption3 == 1) {
                    LOGGER.log(Level.INFO, "Начало блока при изменении стоимости фильма");
                    System.out.println("Ведите желаемую цену...");
                    LOGGER.log(Level.WARNING, "Ввод новой стоимости фильма");
                    changedPrice = scanner.nextLine();
                }
            } else {
                LOGGER.log(Level.INFO, "Начало блока если отказано в изменении даты ");
                System.out.println("Хотите изменить стоимость?");
                LOGGER.log(Level.INFO, "Вызов метода по выводу текста меню в консоль");
                printMenu(optionText);
                LOGGER.log(Level.INFO, "Вызов метода по проверке ввода пункта меню");
                int userOption2 = checkInputtedNumber(2, optionText);

                if (userOption2 == 1) {
                    LOGGER.log(Level.INFO, "Вызов блока для ввода новой стоимости фильлма");
                    System.out.println("Введите желаемую цену...");
                    LOGGER.log(Level.WARNING, "Ввод новой стоимости фильма");
                    changedPrice = scanner.nextLine();
                } else {
                    LOGGER.log(Level.WARNING, "Изменения никуда не вносились");
                    System.out.println("Нечего менять.");
                    LOGGER.log(Level.INFO, "Вызов меню");
                    startMenu();
                }
            }
            LOGGER.log(Level.INFO, "Вызов метода по обновлению данных фильма");
            getFilmService().updateFilmInfo(filmIdToChange, changedDataTime, changedPrice);
            System.out.println("Фильм обновлен!");

        } else {
            LOGGER.log(Level.INFO, "Вызов блока, если дата фильма не просрочена");
            LOGGER.log(Level.INFO, "Изменение запрещено");
            System.out.println("Дата фильма еще действительна! Фильм изменять нельзя!!!");
            LOGGER.log(Level.INFO, "Вызов меню");
            startMenu();
        }
    }

    private void printTicketUsingFilmID() {

        LOGGER.log(Level.INFO, "Начало метода по выводу филетов фильма в консоль");
        LOGGER.log(Level.INFO, "Вызов метода, который проверит и получит айди фильма");
        int filmID = checkFilmID();
        LOGGER.log(Level.INFO, "Вызов метода, который получит список билетов фильма из БД");
        List<Ticket> ticketList = getTicketService().getAllTicketUsingFilmID(filmID);
        LOGGER.log(Level.INFO, "Вывод билетов фильма в консоль");
        ticketList.forEach(System.out::println);
    }

    private void printAllTicket() {

        LOGGER.log(Level.INFO, "Начало метода по выводу билетов в консоль");
        LOGGER.log(Level.INFO, "Вызов метода по получению списка билетов из БД");
        List<Ticket> ticketList = getTicketService().getAllTickets();
        LOGGER.log(Level.INFO, "Вывод билетов в консоль");
        ticketList.forEach(System.out::println);
    }


    private void addTicketToFilm() {

        LOGGER.log(Level.INFO, "Начало метода по добалению билетов к фильму");
        System.out.println("Меню для создания билета.");
        Ticket ticket = new Ticket();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID фильма, к которому хотите добавить билеты...");
        LOGGER.log(Level.INFO, "Вызов метода по получению и проверке введенного айди фильма");
        int filmID = checkFilmID();
        LOGGER.log(Level.INFO, "Вызов метода по получени. списка данных фильма в БД");
        List<String> filmListInfo = getFilmService().getFilmInfoForTicketUse(filmID);
        System.out.println("Введите количество билетов...");
        int ticketAmount = 0;
        boolean isTicket = false;
        do {
            try {
                LOGGER.log(Level.WARNING, "Ввод количества добавляемых билетов");
                ticketAmount = scanner.nextInt();
                isTicket = true;

            } catch (InputMismatchException e) {
                LOGGER.log(Level.WARNING, "Введено не число");
                System.out.println("Вводить необходимо только цифры! \nПопробуйте ещё раз.");
                System.out.println("Введите количество билетов...");
                isTicket = false;
                LOGGER.log(Level.INFO, "Повторный ввод");
                scanner.nextLine();
            }
        } while (isTicket == false);

        List<Ticket> ticketList = new ArrayList(ticketAmount);
        for (int i = 0; i < ticketAmount; i++) {
            LOGGER.log(Level.INFO, "Получение инфо из списка");
            ticket.setTicketFilmName(filmListInfo.get(0));
            ticket.setTicketPrice(filmListInfo.get(1));
            ticket.setTicketFilmTime(filmListInfo.get(2));
            LOGGER.log(Level.INFO, "Вызов метода по созданию билета фильма в Бд");
            getTicketService().createTicketWithFilmID(ticket, filmID);
            System.out.println("Билет создан.");
            ticketList.add(ticket);
        }
    }

    private void printUserByID() {

        LOGGER.log(Level.INFO, "Начало метода по выводу пользователя в консоль");
        LOGGER.log(Level.INFO, "Вызов метода по получению и проверке айди пользователя");
        int userID = checkUserIDinJDBC();
        Person person = getPersonService().printUserByID(userID);
        LOGGER.log(Level.INFO, "Вывод пользователя в консоль");
        System.out.println(person);
        LOGGER.log(Level.INFO, "Вывод билетов пользователя в консоль");
        List<Ticket> userTicketList = getTicketService().getTicketUsingUserID(userID);
        userTicketList.forEach(System.out::println);
    }

    private int checkUserIDinJDBC() {

        LOGGER.log(Level.INFO, "Начало метода по получению и проверке введенного айди пользователя");
        int inputtedUserID = 0;
        Scanner scanner = new Scanner(System.in);
        boolean isUserInJDBC = false;
        System.out.println("Введите ID пользователя...");
        while (!isUserInJDBC) {
            int count = 0;
            do {
                try {
                    LOGGER.log(Level.WARNING, "Ввод айди пользователя");
                    inputtedUserID = scanner.nextInt();
                    LOGGER.log(Level.INFO, "Проверка айди пользователя на существование в БД");
                    isUserInJDBC = getPersonService().checkPersonInDBbyID(inputtedUserID);

                    if (!isUserInJDBC) {
                        LOGGER.log(Level.INFO, "Вызов блока, если пользователя с введенным айди не найдено");
                        System.out.println("Такого пользователя не существует! Введите корректное ID.");
                        count++;

                        if (count == 1) {
                            String[] optionText = {
                                    "Введите - 1 -  да.",
                                    "Введите - 2 -  нет."};
                            System.out.println("Хотите посмотреть список доступных пользователей?");
                            LOGGER.log(Level.INFO, "Вызов метода для вывода текста минименю в консоль");
                            super.printMenu(optionText);
                            LOGGER.log(Level.INFO, "Проверка введеннкого пункта меню");
                            int choice = super.checkInputtedNumber(2, optionText);

                            if (choice == 1) {
                                LOGGER.log(Level.INFO, "Вызов метода по выводу всех пользователей в консоль");
                                printAllUser();
                                System.out.println("Введите айди пользователя...");
                            } else {
                                LOGGER.log(Level.INFO, "Повторный ввод айди пользователя");
                                System.out.println("Введите айди пользователя...");
                                break;
                            }
                            break;
                        }
                        break;
                    }
                    break;

                } catch (InputMismatchException e) {
                    LOGGER.log(Level.WARNING, "Введено не число");
                    System.out.println("Вводить необходимо только цифры! \nПопробуйте ещё раз.");
                    System.out.println("Введите ID пользователя...");
                    LOGGER.log(Level.INFO, "Повторный ввод");
                    scanner.nextLine();
                }
            } while (true);
        }
        return inputtedUserID;
    }

    private void printAllUser() {

        LOGGER.log(Level.INFO, "Начало метода по выводу в консоль всех пользователей");
        LOGGER.log(Level.INFO, "Вызов метода по получению списка пользователей из БД");
        List<Person> userList = getPersonService().getAllPersons();
        LOGGER.log(Level.INFO, "Вывод в консоль пользователей");
        userList.forEach(System.out::println);
    }

    private void printAllFilm() {

        LOGGER.log(Level.INFO, "Начало метода по выводу в консоль всех фильмов");
        LOGGER.log(Level.INFO, "Вызов метода по получению из БД списка всех фильмов");
        List<Film> filmList = getFilmService().getAllFilm();
        LOGGER.log(Level.INFO, "Вывод в консоль всех фильмов");
        filmList.forEach(System.out::println);
    }

    private void createFilm() {

        LOGGER.log(Level.INFO, "Начало метода по созданию фильма");
        System.out.println("Меню для создания фильма.");
        Film film = new Film();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название фильма...");
        LOGGER.log(Level.WARNING, "Ввод названия фильма");
        String filmName = scanner.nextLine();
        film.setFilmName(filmName);
        LOGGER.log(Level.WARNING, "Ввод жанра фильма");
        System.out.println("Введите жанр фильма...");
        String filmType = scanner.nextLine();
        film.setFilmType(filmType);
        LOGGER.log(Level.WARNING, "Ввод стоимости фильма");
        System.out.println("Введите стоимость фильма...");
        String filmPrice = scanner.nextLine();
        film.setFilmPrice(filmPrice);
        LOGGER.log(Level.WARNING, "Ввод даты и времени проведения фильма");
        System.out.println("Введите дату и время показа фильма в формате dd-MM-yyyy HH:mm");
        String filmDateTime = scanner.nextLine();
        film.setFilmTime(filmDateTime);
        LOGGER.log(Level.INFO, "Вызов метода по созданию фильма в БД");
        getFilmService().create(film);
        System.out.println("Фильм создан!");
    }
}
