package com.senla.controller;

import com.senla.Main;
import com.senla.model.Film;
import com.senla.model.Person;
import com.senla.model.Ticket;
import com.senla.service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonMenu extends MainCinemaMenu {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private final PersonService personService = new PersonServiceImpl();
    private final FilmService filmService = new FilmServiceImpl();
    private final TicketService ticketService = new TicketServiceImpl();
    private String myLogin = null;

    public String getMyLogin() {
        return myLogin;
    }

    public void setMyLogin(String myLogin) {
        this.myLogin = myLogin;
    }

    public PersonService getPersonService() {
        return personService;
    }

    public FilmService getFilmService() {
        return filmService;
    }

    public TicketService getTicketService() {
        return ticketService;
    }

    public void startUserMenu() {

        LOGGER.log(Level.INFO, "Начало меню пользователя");
        System.out.println("Добро пожаловать в меню пользователя! ");
        String[] userMenuText = {
                " Введите - 1 - для просмотра доступных фильмов",
                " Введите - 2 - для покупки билета",
                " Введите - 3 - для просмотра купленых билетов",
                " Введите - 4 - для возврата билета",
                " Введите - 5 - для просмотра остатка на счету",
                " Введите - 6 - для пополнения счета",
                " Введите - 7 - для выхода из приложения "};
        int option;
        do {
            LOGGER.log(Level.INFO, "Вызываем метод по выводу текст меню на консоль");
            printMenu(userMenuText);
            LOGGER.log(Level.WARNING, "Пользователь вводит номер меню");
            option = checkInputtedNumber(countMenuLengthFromMenuArray(userMenuText), userMenuText);

            switch (option) {
                case 1 -> {
                    LOGGER.log(Level.INFO, "Вызов метода по выводу в консоль всех фильмов");
                    System.out.println("Смотрим фильмы...");
                    printAllFilm();
                }
                case 2 -> {
                    LOGGER.log(Level.INFO, "Вызов метода по покупке билета");
                    System.out.println("Переходим к покупке билета...");
                    buyTicket();
                }
                case 3 -> {
                    LOGGER.log(Level.INFO, "Вызов метода по выводу в консоль купленных билетов");
                    System.out.println("Смотрим купленные билеты...");
                    printUserTicket();
                }
                case 4 -> {
                    LOGGER.log(Level.INFO, "Вызов метода по возврату билета");
                    System.out.println("Возврат билета...");
                    returnTicket();
                }
                case 5 -> {
                    System.out.println("Просмотр остатка на счету...");
                    LOGGER.log(Level.INFO, "Вызов метода по выводу в консоль актуального баланса");
                    printUserMoney();
                }
                case 6 -> {
                    LOGGER.log(Level.INFO, "Вызов метода для пополнения счета");
                    System.out.println("Пополнение счета...");
                    addUserMoney();
                }
                case 7 -> {
                    LOGGER.log(Level.INFO, "Приложение окончило работу");
                    System.out.println("Приложение закончило работу.");
                    System.exit(0);
                }
            }
        } while (option != 5);
    }

    public void startIfPersonIsNotAuthorised() {

        LOGGER.log(Level.INFO, "Начало работы метода, если пользователь не зарегистрирован");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Меню для регистрации с созданием пользователя");
        Person person = new Person();
        LOGGER.log(Level.INFO, "Вызов метода, который получит валидный логин нового пользователя");
        String userLogin = checkLogin();
        person.setPersonLogin(userLogin);
        System.out.println("Введите пароль");
        LOGGER.log(Level.WARNING, "Пользователь вводит пароль");
        String password = scanner.nextLine();
        person.setPersonPassword(password);
        LOGGER.log(Level.INFO, "Вызов метода создания пользователя");
        getPersonService().create(person);
        System.out.println("Регистрация прошла успешно!");
        LOGGER.log(Level.INFO, "Вызов главного меню приложения");
        startMenu();
    }

    public void startMenuIfPersonIsAuthorised() {

        LOGGER.log(Level.INFO, "Начало меню, когда пользователь уже зарегистрирован");
        String[] menuOptionIfPersonIsAuthorised = {
                " Введите - 1 - для ввода Логина и пароля.",
                " Введите - 2 - для выхода из приложения."};

        LOGGER.log(Level.INFO, "Вызов метода, выводящего текст меню на консоль");
        printMenu(menuOptionIfPersonIsAuthorised);
        LOGGER.log(Level.INFO, "Вызов метода, который проверяет введенное пользователем число на соответсвие меню");
        int option = checkInputtedNumber(countMenuLengthFromMenuArray(menuOptionIfPersonIsAuthorised), menuOptionIfPersonIsAuthorised);

        switch (option) {
            case 1 -> {
                LOGGER.log(Level.INFO, "Начало блока по вводу логина и пароля");
                System.out.println("Пожалуйста, введите ваш Логин и Пароль.");
                LOGGER.log(Level.INFO, "Вызов метода, который принесет уровень доступа пользователя");
                String foundedUserRole = userAuthorisation();
                ManagerMenu managerMenu = new ManagerMenu();
                AdminMenu adminMenu = new AdminMenu();

                switch (foundedUserRole) {
                    case "ORDINARY_USER" -> {
                        LOGGER.log(Level.INFO, "Перенаправление в меню обычного польщователя");
                        startUserMenu();
                    }
                    case "MANAGER" -> {
                        LOGGER.log(Level.INFO, "Перенаправление в меню менеджера");
                        managerMenu.startMenu();
                    }
                    case "ADMIN" -> {
                        LOGGER.log(Level.INFO, "Перенаправление в меню администратора");
                        adminMenu.startMenu();
                    }
                }
            }
            case 2 -> {
                LOGGER.log(Level.INFO, "Приложение закончило работу");
                System.out.println("Приложение закончило работу");
                System.exit(0);
            }
        }
    }

    private int checkUserInputtedTicketID() {

        LOGGER.log(Level.INFO, "Запуск метода проверки введенного пользователем айди билета");
        boolean CorrectValue;
        int result = 0;
        do {
            try {
                CorrectValue = true;
                Scanner scan1 = new Scanner(System.in);
                LOGGER.log(Level.WARNING, "Пользователь вводит айди билета");
                int ticketReturnID = scan1.nextInt();
                LOGGER.log(Level.INFO, "Вызов метода, который получит список айди билетов пользователя");
                List<Integer> userTicketIDList = getTicketService().getTicketIDListWithUserLogin(getMyLogin());
                boolean isUserInputtedNumberExistsInTicketListId;
                LOGGER.log(Level.INFO, "Вызов метода по проверке введенного пользователем айди билета со списком");
                isUserInputtedNumberExistsInTicketListId = checkUserInputtedTicketIDWithExistedId(ticketReturnID, userTicketIDList);

                if (isUserInputtedNumberExistsInTicketListId == false) {
                    LOGGER.log(Level.WARNING, "Пользователь ввел не существующий айди");
                    System.out.println("Такого айди билета не существует. Введите верное айди");
                    CorrectValue = false;
                } else {
                    LOGGER.log(Level.WARNING, "Пользователь ввел коректные данные");
                    result = ticketReturnID;
                }
            } catch (InputMismatchException e) {
                LOGGER.log(Level.WARNING, "Пользователь ввел неверный формат данных");
                CorrectValue = false;
                System.out.println("Вводить необходимо только цифры! \nПопробуйте ещё раз");
            }
        } while (!CorrectValue);
        return result;
    }

    private boolean checkUserInputtedTicketIDWithExistedId(int userInputtedID, List<Integer> userTicketIDList) {

        LOGGER.log(Level.INFO, "Начало метода, который выполняет проверку на наличие у пользователя билетов");
        boolean input;
        input = false;

        if (userTicketIDList.isEmpty()) {
            LOGGER.log(Level.INFO, "Билетов у пользователя нет");
            System.out.println("У вас нет билетов");
        } else {
            LOGGER.log(Level.INFO, "Билеты есть, находим совпавший айди со списком");
            for (Integer item : userTicketIDList) {
                if (item.equals(userInputtedID)) {
                    input = true;
                    break;
                } else {
                    input = false;
                }
            }
        }
        return input;
    }

    private void printUserTicket() {

        LOGGER.log(Level.INFO, "Начало работы метода по выводу на консоль списка билетов пользователя");
        int userID = getPersonService().getUserIDbyLogin(getMyLogin());
        LOGGER.log(Level.INFO, "Получили из БД айди пользователя по логину");
        List<Ticket> userTicketList = getTicketService().getTicketUsingUserID(userID);
        LOGGER.log(Level.INFO, "Получили список билетов пользователя");

        if (userTicketList.isEmpty()) {
            LOGGER.log(Level.WARNING, "Список билетов пуст");
            System.out.println("У вас нет билетов");
        } else {
            LOGGER.log(Level.INFO, "Вывод на консоль списка билетов пользователя");
            userTicketList.forEach(System.out::println);
        }
    }

    private void addUserMoney() {

        LOGGER.log(Level.INFO, " Начало работы метода, который пополнит балланс пользователя");
        LOGGER.log(Level.INFO, "Вызов меотда, который достанет актуальный баланс у пользователя из БД");
        String userMoneyInDB = getPersonService().getUserMoney(getMyLogin());

        try {
            LOGGER.log(Level.WARNING, "Может быть ошибка, если балланс в БД null");
            int finUserMoney = Integer.parseInt(userMoneyInDB);
            System.out.println("Введите сумму для пополнения счета. ВНИМАНИЕ! Максимальный балланс 1000 рублей");
            LOGGER.log(Level.INFO, "Вызов метода, который получит введенные пользователем деньги");
            int userInputtedMoney = checkInputtedMoney(finUserMoney);
            LOGGER.log(Level.INFO, "Вызов метода, который запишет в БД деньги на баланс пользователя");
            getPersonService().addMoneyWithLogin(getMyLogin(), userInputtedMoney);
            printUserMoney();

        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Балланс пользователя был null");
            int moneyBalanceNull = 0;
            System.out.println("Введите сумму для пополнения счета. ВНИМАНИЕ! Максимальный балланс 1000 рублей");
            LOGGER.log(Level.INFO, "Вызов метода, который получит введенные пользователем деньги");
            int userInputtedMoney = checkInputtedMoney(moneyBalanceNull);
            LOGGER.log(Level.INFO, "Вызов метода, запишет в БД деньги на баланс пользователя");
            getPersonService().addMoneyWithLogin(getMyLogin(), userInputtedMoney);
            LOGGER.log(Level.INFO, "Вызов метода по выводу актуального баланса пользователя в консоль");
            printUserMoney();
        }
    }

    private int checkInputtedMoney(int userAccountMoneyExist) {

        LOGGER.log(Level.INFO, "Начало работы метода, который проверит введеные пользователем данные на деньги");
        boolean CorrectValue;
        int result = 0;
        do {
            try {
                CorrectValue = true;
                Scanner scanner = new Scanner(System.in);
                LOGGER.log(Level.WARNING, "Пользователь вводит денги");
                int select2 = scanner.nextInt();

                if (userAccountMoneyExist + select2 >= 1000) {
                    LOGGER.log(Level.WARNING, "Пользователь ввел слишком много денег");
                    System.out.println("У вас будет слишком много денег! Наше приложение не банк! Мы столько не храним!");
                    System.out.println("Пожалуйста, введите меньшую сумму...");
                    CorrectValue = false;

                } else if (userAccountMoneyExist + select2 <= 0) {
                    LOGGER.log(Level.WARNING, "Пользователь ввел недостаточную сумму");
                    System.out.println("У вас будет слишком мало денег!");
                    System.out.println("Пожалуйста, введите большую сумму...");
                    CorrectValue = false;

                } else {
                    LOGGER.log(Level.INFO, "Сумма введена верно");
                    result = select2;
                    System.out.println(result);
                }

            } catch (InputMismatchException e) {
                LOGGER.log(Level.WARNING, "Пользователь ввел не денги");
                CorrectValue = false;
                System.out.println("Вводить необходимо только цифры! \nПопробуйте ещё раз.");
                System.out.println("Пожалуйста, введите деньги...");
            }
        } while (!CorrectValue);
        System.out.println(result);
        return result;
    }

    private void printUserMoney() {

        LOGGER.log(Level.INFO, "Начало метода, который выведет в консоль текущий баланс пользователя");
        LOGGER.log(Level.INFO, "Вызов метода, который достанет из БД текущий баланс пользователя");
        String userMoney = getPersonService().getUserMoney(getMyLogin());
        System.out.println("Ваш текущий баланс составляет " + userMoney + " рублей.");
        LOGGER.log(Level.INFO, "Вызов главного меню пользователя");
        startUserMenu();
    }

    private void returnTicket() {

        LOGGER.log(Level.INFO, "Начало метода по возврату пользователем билета");
        LOGGER.log(Level.INFO, "Вызов метода, который выведет в консоль список купленых билетов пользователя");
        printUserTicket();
        System.out.println("Введите айди билета, который хотите вернуть");
        LOGGER.log(Level.INFO, "Вызов метода который получит и проверит введеный пользователем айди билета");
        int userInputtedTicketID = checkUserInputtedTicketID();
        LOGGER.log(Level.INFO, "Вызов метода, который достанет из БД дату-время билета пользователя");
        String ticketDateTimeFromDB = getTicketService().getTicketDataTime(userInputtedTicketID);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime ticketDateTime = LocalDateTime.parse(ticketDateTimeFromDB, formatter);
        LocalDateTime now = LocalDateTime.now();
        LOGGER.log(Level.INFO, "Вызов провери даты билета на актуальность");
        boolean isBefore = ticketDateTime.isBefore(now);

        if (isBefore == true) {
            LOGGER.log(Level.INFO, "Билет просрочен");
            System.out.println("Билет был использован. Вернуть нельзя!");
        } else {
            LOGGER.log(Level.INFO, "Билет действителен");
            LOGGER.log(Level.INFO, "Вызов метода, котрый достанет цену билета из БД");
            int ticketPrice = getTicketService().getTicketPrice(userInputtedTicketID);
            LOGGER.log(Level.INFO, "Вызов метода, который исправит сам билет в БД");
            getTicketService().updateReturnedTicketInfo(userInputtedTicketID);
            LOGGER.log(Level.INFO, "Вызов метода, который вернет деньги пользователю");
            getPersonService().updateUserAndAdminMoneyReturned(ticketPrice, getMyLogin());
            System.out.println("Билеты возвращены!");
        }
    }

    private void buyTicket() {

        LOGGER.log(Level.INFO, "Начало метода по покупке билета");

        System.out.println("Введите айди фильма, на который хотите купить билет...");
        AdminMenu adminMenu = new AdminMenu();
        LOGGER.log(Level.INFO, "Вызов метода по получению и проверке от пользователя айди фильма, на который идет покупка билета");
        int filmId = adminMenu.checkFilmID();

        LOGGER.log(Level.INFO, "Вызов метода по получению инфо фильма");
        List<String> filmListInfo = getFilmService().getFilmInfoForTicketUse(filmId);
        String filmName = filmListInfo.get(0);
        String filmPrice = filmListInfo.get(1);
        String filmTime = filmListInfo.get(2);

        LOGGER.log(Level.INFO, "Проверяем дату выбранного фильма на актуальность");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime ticketDateTime = LocalDateTime.parse(filmTime, formatter);
        LocalDateTime now = LocalDateTime.now();
        boolean isBefore = ticketDateTime.isBefore(now);

        if (isBefore == true) {
            LOGGER.log(Level.WARNING, "Пользователь захотел купить билет на закончившийся фильм");
            System.out.println("Фильм уже закончился! Пожалуйста, выберите другой фильм!");
            printAllFilm();
            buyTicket();
        } else {
            LOGGER.log(Level.INFO, "Фильм выбран коректно, получаем подтверждение пользователя на покупку");
            System.out.println("Вы действительно хотите купить билет на фильм " + filmName +
                    ", стоимость билета " + filmPrice + " рублей. Время сеанса: " + filmTime);
            String[] optionText = {
                    " Введите - 1 - да.",
                    " Введите - 2 - нет."};
            String[] option2Text = {
                    " Введите - 1 - Чтобы вернуться в главное меню пользователя.",
                    " Введите - 2 - Чтобы вернуться к вводу айди фильма.",
                    " Введите - 3 - Чтобы выйти из приложения."};

            LOGGER.log(Level.INFO, "Запуск метода по выводу текста меню в консоль");
            printMenu(optionText);
            LOGGER.log(Level.INFO, "Вызов метода по проверке и получению выбора номера меню пользователем");
            int inputtedChoice = checkInputtedNumber(2, optionText);

            switch (inputtedChoice) {
                case 1 -> {
                    LOGGER.log(Level.INFO, "Покупка подтверждена пользователем");
                    System.out.println("Переходим к покупке");
                    List<Ticket> ticketListNotSold = getTicketService().getNotSoldTicketList(filmId);

                    LOGGER.log(Level.INFO, "Выводим доступные билеты фильма на экран");
                    ticketListNotSold.forEach(System.out::println);
                    int notSoldTicketAmount = ticketListNotSold.size();
                    System.out.println("Сколько билетов вы хотите купить? На этот фильм доступно к продаже " +
                            notSoldTicketAmount + " билетов.");
                    System.out.println("Введите число билетов:");

                    LOGGER.log(Level.WARNING, "Пользователь вводит желаемое к покупке количество билетов");
                    int userWantedTicketAmount = checkInputtedNumberForTicketAmount(notSoldTicketAmount);
                    int userMoneyBalance = getUserMoney();
                    LOGGER.log(Level.INFO, "Вызов метода по проверке баланса");
                    boolean isMoneyEnough = isUserMoneyEnough(Integer.parseInt(filmPrice), userWantedTicketAmount, userMoneyBalance);


                    if (isMoneyEnough == false) {
                        LOGGER.log(Level.WARNING, "У пользователя недостаточно денег на балансе");
                        System.out.println("У вас недостаточно денег на баллансе!");
                        String[] option3Text = {
                                " Введите - 1 - Чтобы пополнить балланс",
                                " Введите - 2 - Чтобы перейти в главное меню пользователя",
                                " Введите - 3 - Чтобы выйти из приложения."};
                        LOGGER.log(Level.INFO, "Вызов метода выода текста меню в консоль");
                        printMenu(option3Text);
                        LOGGER.log(Level.INFO, "Обрабатываем введенные пользователем номер меню");
                        int userInputtedNumber = checkInputtedNumber(3, option3Text);
                        switch (userInputtedNumber) {
                            case 1 -> {
                                LOGGER.log(Level.INFO, "Вызов метода по пополнению баланса пользователя");
                                addUserMoney();
                            }
                            case 2 -> {
                                LOGGER.log(Level.INFO, "Вызов главного меню пользователя");
                                startUserMenu();
                            }
                            case 3 -> {
                                LOGGER.log(Level.INFO, "Приложение окончило работу");
                                System.exit(0);
                            }
                        }
                    } else {
                        LOGGER.log(Level.INFO, "Денег достаточно");
                        LOGGER.log(Level.INFO, "Вызов метода, который вернет айди пользователя по логину из БД");
                        int userID = getPersonService().getUserIDbyLogin(getMyLogin());
                        List<Integer> ticketList = new ArrayList<>(userWantedTicketAmount);

                        for (int i = 0; i < userWantedTicketAmount; i++) {
                            LOGGER.log(Level.INFO, "Вызов метода, который добавит айди пользователя к купленым билетам");
                            getTicketService().addUserIDToTicketWithFilmID(userID, filmId);
                            ticketList.add(i);
                        }
                        LOGGER.log(Level.INFO, "Начало списания денег");
                        int ticketFinalPrice = Integer.parseInt(filmPrice) * userWantedTicketAmount;
                        LOGGER.log(Level.INFO, "Вызов метода по списанию денег с пользователя и переводу их в баланс кинотеатра");
                        getPersonService().updateUserAndAdminMoney(ticketFinalPrice, getMyLogin());
                        System.out.println("Билеты куплены!");
                    }
                }
                case 2 -> {
                    LOGGER.log(Level.INFO, "Пользователь не желает продолжить покупку билета");
                    LOGGER.log(Level.INFO, "Вызов метода по выводу текста меню в консоль");
                    printMenu(option2Text);
                    LOGGER.log(Level.INFO, "Вызов метода по проверке введеного число номеру меню");
                    int userChoice2 = checkInputtedNumber(3, option2Text);

                    switch (userChoice2) {
                        case 1 -> {
                            LOGGER.log(Level.INFO, "Вызов главного меню пользователя");
                            System.out.println("Переходим в главное меню пользователя...");
                            startUserMenu();
                        }
                        case 2 -> {
                            LOGGER.log(Level.INFO, "Вызов метода по выводу всех фильмов на экран");
                            System.out.println("Переходим к выбору фильма...");
                            printAllFilm();
                            LOGGER.log(Level.INFO, "Вызов метода по покупке билета");
                            buyTicket();
                        }
                        case 3 -> {
                            LOGGER.log(Level.INFO, "Приложение закончило работу");
                            System.exit(0);
                        }
                    }
                }
            }
        }
    }

    private int getUserMoney() {

        LOGGER.log(Level.INFO, "Проверяем наличие денег у пользователя");
        String userMoneyFromDB = getPersonService().getUserMoney(getMyLogin());
        int userMoney;
        try {
            userMoney = Integer.parseInt(userMoneyFromDB);
            return userMoney;
        } catch (NumberFormatException e) {
            int moneyBalanceNull = 0;
            return moneyBalanceNull;
        }
    }

    private boolean isUserMoneyEnough(int ticketPrice, int userWantedTicketAmount, int userAllMoney) {

        LOGGER.log(Level.INFO, "Начало метода по проверке стоимости билетов к покупке с балансом пользователя");
        int userFinalTicketPrice = ticketPrice * userWantedTicketAmount;

        if (userAllMoney >= userFinalTicketPrice) {
            LOGGER.log(Level.INFO, "Денег достаточно");
            return true;
        } else {
            LOGGER.log(Level.INFO, "Денег не достаточно");
            return false;
        }
    }

    private int checkInputtedNumberForTicketAmount(int maxTicketAmount) {

        LOGGER.log(Level.INFO, "Начало метода по проверке ввода пользователем числа билетов");
        boolean CorrectValue;
        int result = 0;
        do {
            try {
                CorrectValue = true;
                Scanner scanner = new Scanner(System.in);
                LOGGER.log(Level.WARNING, "Пользователь вводит данные");
                int select2 = scanner.nextInt();

                if (select2 < 1 || select2 > maxTicketAmount) {
                    LOGGER.log(Level.WARNING, "Пользователь ввел неверное количество билетов");
                    System.out.println("Вы ввели неверную цифру, попробуйте ещё раз.");
                    CorrectValue = false;
                } else {
                    LOGGER.log(Level.INFO, "Данные введены верно");
                    result = select2;
                }

            } catch (InputMismatchException e) {
                LOGGER.log(Level.INFO, "Пользователь ввел не количество билетов");
                CorrectValue = false;
                System.out.println("Вводить необходимо только цифры! \nПопробуйте ещё раз.");
            }
        } while (!CorrectValue);
        return result;
    }


    private String checkLogin() {

        LOGGER.log(Level.INFO, "Начало работы метода, который проверит введеный логин на доступность в БД");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите желаемый логин");
        LOGGER.log(Level.WARNING, "Пользователь вводит логин");
        String personWantedLogin = scanner.nextLine();
        LOGGER.log(Level.INFO, "Вызов метода, который проверит занят ли логин польователя кем-то в БД");
        boolean isLoginInDB = getPersonService().checkPersonLoginInDB(personWantedLogin);
        while (isLoginInDB == true) {
            LOGGER.log(Level.WARNING, "Пользователь с таким логином уже существет");
            System.out.println("Такой логин уже существует. Введите другой логин!");
            LOGGER.log(Level.WARNING, "Пользователь вводит другой логин");
            personWantedLogin = scanner.nextLine();
            LOGGER.log(Level.INFO, "Вызов метода, который проверит занят ли логин польователя кем-то в БД");
            isLoginInDB = getPersonService().checkPersonLoginInDB(personWantedLogin);
        }
        return personWantedLogin;
    }


    private String userAuthorisation() {

        LOGGER.log(Level.INFO, "Начало метода по авторизации пользователя в системе");
        Scanner scanner = new Scanner(System.in);
        int counter = 1;
        System.out.println("Введите логин и пароль. У вас всего 3 попытки. Попытка № - " + counter);
        boolean isQuit = false;
        String foundedUserRole = null;

        while (!isQuit) {
            System.out.println("Введите логин");
            LOGGER.log(Level.WARNING, "Пользователь вводит логин");
            setMyLogin(scanner.nextLine());
            System.out.println("Введите пароль");
            LOGGER.log(Level.WARNING, "Пользователь вводит пароль");
            String myPassword = scanner.nextLine();
            Person person;
            LOGGER.log(Level.WARNING, "Вызов метода, который достает из БД пользователя согласно полученному логину и паролю");
            person = getPersonService().checkPersonForExistenceByLogin(getMyLogin(), myPassword);

            if (person != null) {
                LOGGER.log(Level.INFO, "Пользователь был найден");
                LOGGER.log(Level.INFO, "Вызов метода, который достает из БД роль пользователья по его логину");
                foundedUserRole = getPersonService().checkPersonInDB(getMyLogin());
                isQuit = true;
            } else {
                LOGGER.log(Level.WARNING, "Пользователь ввел неверные логин или пароль");
                counter++;
                System.out.println("Неверный логин или пароль! ");

                if (counter == 4) {
                    LOGGER.log(Level.WARNING, "Попытки ввода логина и пароля закончились. Приложение закончило работу");
                    System.out.println("Попытки закончились. До свидания!");
                    System.exit(0);
                } else {
                    LOGGER.log(Level.INFO, "Пользователь использует еще одну попытку");
                    System.out.println("Попытка номер " + counter + ". Введите данные: ");
                }
            }
        }
        return foundedUserRole;
    }

    private void printAllFilm() {

        LOGGER.log(Level.INFO, "Работа метода по выводу всех фильмов кинотеатра");
        LOGGER.log(Level.INFO, "Вызов метода, который запишет в список все фильмы");
        List<Film> filmList = getFilmService().getAllFilm();
        LOGGER.log(Level.INFO, "Вывод списка фильмов в консоль");
        filmList.forEach(System.out::println);
    }
}
