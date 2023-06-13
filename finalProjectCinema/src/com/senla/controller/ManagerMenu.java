package com.senla.controller;

import com.senla.Main;
import com.senla.model.Film;
import com.senla.model.Ticket;
import com.senla.service.FilmService;
import com.senla.service.FilmServiceImpl;
import com.senla.service.TicketService;
import com.senla.service.TicketServiceImpl;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagerMenu extends MainCinemaMenu {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private final FilmService filmService = new FilmServiceImpl();
    private final TicketService ticketService = new TicketServiceImpl();

    public FilmService getFilmService() {
        return filmService;
    }

    public TicketService getTicketService() {
        return ticketService;
    }

    @Override
    public void startMenu() {

        LOGGER.log(Level.INFO, "Начло работы меню менеджера");
        System.out.println("Добро пожаловать в Меню менеджера");
        String[] userMenuText = {
                " Введите - 1 - для просмотра доступных фильмов",
                " Введите - 2 - для редактирования фильма",
                " Введите - 3 - для добавления билета к фильму",
                " Введите - 4 - для просмотра всех билетов конкретного фильма",
                " Введите - 5 - для выхода из приложения "};
        int option = 1;
        do {
            LOGGER.log(Level.INFO, "Вызов метода по выводу текста меню в консоль");
            printMenu(userMenuText);
            LOGGER.log(Level.INFO, "Вызов метода, который проверит и получит введенный пункт меню");
            option = checkInputtedNumber(countMenuLengthFromMenuArray(userMenuText), userMenuText);

            switch (option) {
                case 1 -> {
                    LOGGER.log(Level.INFO, "Вызов метода по выводу всех фильмов в консоль");
                    System.out.println("Просмотр доступных фильмов");
                    printAllFilm();
                }
                case 2 -> {
                    LOGGER.log(Level.INFO, "Вызов метода, который редактирует данные фильма");
                    System.out.println("Редактироавть фильм...");
                    changeFilmInfo();
                }
                case 3 -> {
                    LOGGER.log(Level.INFO, "Вызов метода по добавлению билетов к фильму");
                    System.out.println("Добавление билета к фильму...");
                    addTicketToFilm();
                }
                case 4 -> {
                    LOGGER.log(Level.INFO, "Вызов метода по выводу всех билетов фильма в консоль");
                    System.out.println("Просмотр всех билетов конкретного фильма...");
                    printTicketUsingFilmID();
                }
                case 5 -> {
                    LOGGER.log(Level.INFO, "Приложение закончило работу");
                    System.out.println("Приложение закончило работу.");
                    System.exit(0);
                }
            }
        } while (true);
    }

    private void printAllFilm() {

        LOGGER.log(Level.INFO, "Начало приложения по выводу списка всех фильмов в консоль");
        LOGGER.log(Level.INFO, "Вызов метода, который получит список всех фильмов кинотеатра из БД");
        List<Film> filmList = getFilmService().getAllFilm();
        LOGGER.log(Level.INFO, "Вывод списка фильмов в консоль");
        filmList.forEach(System.out::println);
    }

    private void printTicketUsingFilmID() {

        LOGGER.log(Level.INFO, "Начало метода по выводу списка билетов фильма в консоль");
        LOGGER.log(Level.INFO, "Вызов метода, который проверит и получит введенный айди фильма");
        int filmID = checkFilmID();
        LOGGER.log(Level.INFO, "Вызов метода, который достанет из БД список билетов фильма");
        List<Ticket> ticketList = getTicketService().getAllTicketUsingFilmID(filmID);
        LOGGER.log(Level.INFO, "Вывод списка билетов фильма в консоль");
        ticketList.forEach(System.out::println);
    }

    private void changeFilmInfo() {

        LOGGER.log(Level.INFO, "");
        System.out.println("Введите айди фильма, который хотите изменить...");
        int filmIdToChange = checkFilmID();
        String changedDataTime = null;
        String changedPrice = null;
        LOGGER.log(Level.INFO, "Вызов метода по проверке даты фильма на актуальность");
        boolean isExpired = getFilmService().isExpired(filmIdToChange);

        if (isExpired == true) {

            LOGGER.log(Level.INFO, "Вызов блока, если дата проведения фильма актуальна");
            System.out.println("Хотите изменить дату и время?");
            Scanner scanner = new Scanner(System.in);
            String[] optionText = {
                    "Введите - 1 - если да.",
                    "Введите - 2 - если нет."};

            LOGGER.log(Level.INFO, "Вызов метода вывода меню в консоль");
            printMenu(optionText);
            LOGGER.log(Level.INFO, "Вызов метода по получению и проверке введенного пункта меню");
            int userOption = checkInputtedNumber(2, optionText);

            if (userOption == 1) {
                LOGGER.log(Level.INFO, "Вызов блока по вводу даты и времени");
                System.out.println("Введите желаемые дату и время, формат: dd-MM-yyyy HH:mm");
                LOGGER.log(Level.WARNING, "Ввод даты и времени");
                changedDataTime = scanner.nextLine();
                System.out.println("Хотите изменить стоимость?");
                LOGGER.log(Level.INFO, "Вызов метода по выводу текста мини-меню");
                printMenu(optionText);
                LOGGER.log(Level.INFO, "Вызов метода по получению и проверке введенного пункта мини-меню");
                int userOption3 = checkInputtedNumber(2, optionText);
                if (userOption3 == 1) {
                    LOGGER.log(Level.WARNING, "Ввод новой стоимости билета");
                    System.out.println("Ведите желаемую цену...");
                    changedPrice = scanner.nextLine();
                }
            } else {
                LOGGER.log(Level.INFO, "Вызов блока по изменению стоимости");
                System.out.println("Хотите изменить стоимость?");
                LOGGER.log(Level.INFO, "Вызов метода по выводу текста мини-меню в консоль");
                printMenu(optionText);
                LOGGER.log(Level.INFO, "Вызов метода по проверке и получению введенного пункта меню");
                int userOption2 = checkInputtedNumber(2, optionText);

                if (userOption2 == 1) {
                    LOGGER.log(Level.WARNING, "Ввод новой стоимости");
                    System.out.println("Введите желаемую цену...");
                    changedPrice = scanner.nextLine();
                } else {
                    LOGGER.log(Level.WARNING, "Пользователь не изменил ни одно поле");
                    System.out.println("Нечего менять.");
                    LOGGER.log(Level.INFO, "Вызов меню");
                    startMenu();
                }
            }
            LOGGER.log(Level.INFO, "Вызов метода по изменению данных фильма");
            getFilmService().updateFilmInfo(filmIdToChange, changedDataTime, changedPrice);
            System.out.println("Фильм обновлен!");


        } else {
            LOGGER.log(Level.WARNING, "Дата проведения фильма еще актуальна. Фильм изменению не подлежит");
            System.out.println("Дата фильма еще действительна! Фильм изменять нельзя!!!");
            LOGGER.log(Level.INFO, "Вызов меню");
            startMenu();
        }
    }

    private int checkFilmID() {

        LOGGER.log(Level.INFO, "Начало метода, который получит и проверит введенный айди фильма");
        int inputtedFilmID = 0;
        Scanner scanner = new Scanner(System.in);
        boolean isFilmInJDBC = false;
        System.out.println("Введите айди фильма...");
        while (!isFilmInJDBC) {
            int count = 0;
            do {
                try {
                    LOGGER.log(Level.WARNING, "Ввод айди фильма");
                    inputtedFilmID = scanner.nextInt();
                    LOGGER.log(Level.INFO, "Вызов метода по проверке введеннкого айди фильма на соответсвие в БД");
                    isFilmInJDBC = getFilmService().checkFilmID(inputtedFilmID);

                    if (!isFilmInJDBC) {
                        LOGGER.log(Level.WARNING, "Айди фильма введено неверно. Такого фильма нет в БД");
                        System.out.println("Такого фильма не существует! Введите корректное айди.");
                        count++;

                        if (count == 1) {
                            LOGGER.log(Level.INFO, "Вызов блока мини-меню");
                            String[] optionText = {
                                    "Введите - 1 - если да.",
                                    "Введите - 2 - если нет."
                            };
                            System.out.println("Хотите посмотреть список доступных фильмов?");
                            super.printMenu(optionText);
                            LOGGER.log(Level.INFO, "Вызов метода по проверке введенного пункта меню");
                            int choice = super.checkInputtedNumber(2, optionText);

                            if (choice == 1) {
                                LOGGER.log(Level.INFO, "Вызов метода по выводу всех фильмов в консоль");
                                printAllFilm();
                                System.out.println("Введите айди фильма...");
                            } else {
                                LOGGER.log(Level.WARNING, "Повторный ввод айди фильма");
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

    private void addTicketToFilm() {

        LOGGER.log(Level.INFO, "Начало метода по добавлению билетов к фильму");
        System.out.println("Меню для создания билета.");
        Ticket ticket = new Ticket();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID фильма, к которому хотите добавить билеты...");

        LOGGER.log(Level.INFO, "Вызов метода по получению и проверке введенного айди фильма");
        int filmID = checkFilmID();
        LOGGER.log(Level.INFO, "Вызов метода по получению списка инфо о фильме из БД");
        List<String> filmListInfo = getFilmService().getFilmInfoForTicketUse(filmID);
        System.out.println("Введите количество билетов...");
        int ticketAmount = 0;
        boolean isTicket = false;

        do {
            try {
                LOGGER.log(Level.WARNING, "Ввод количества билетов");
                ticketAmount = scanner.nextInt();
                System.out.println(ticketAmount);
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
            LOGGER.log(Level.INFO, "Получение данных из списка фильма");
            ticket.setTicketFilmName(filmListInfo.get(0));
            ticket.setTicketPrice(filmListInfo.get(1));
            ticket.setTicketFilmTime(filmListInfo.get(2));
            LOGGER.log(Level.INFO, "Вызов метода по созданию билета");
            getTicketService().createTicketWithFilmID(ticket, filmID);
            System.out.println("Билет создан.");
            ticketList.add(ticket);
        }
    }
}
