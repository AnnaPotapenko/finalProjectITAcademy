package com.senla.controller;

import com.senla.Main;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainCinemaMenu extends MenuForAll {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void startCinemaMenu() {

        LOGGER.log(Level.INFO, "Запускаем основное меню");
        System.out.println("Добро пожаловать в меню нашего кинотеатра!");
        System.out.println("Вы зарегистрированы в нашем кинотеатре?");
        MainCinemaMenu mainCinemaMenu = new MainCinemaMenu();
        mainCinemaMenu.startMenu();
    }

    @Override
    public void startMenu() {

        LOGGER.log(Level.INFO, "Начало работы основного меню");
        String[] myMenuText = {
                "Введите - 1 - если вы зарегистрированы.",
                "Введите - 2 - если вы не зарегистрирвоаны.",
                "Введите - 3 - для выхода из приложения."};
        LOGGER.log(Level.INFO, "Запуск метода по выводу текста меню на консоль");
        printMenu(myMenuText);
        LOGGER.log(Level.INFO, "Запуск метода, в котором пользователь вводит выбор меню");
        int option = checkInputtedNumber(countMenuLengthFromMenuArray(myMenuText), myMenuText);
        PersonMenu personMenu = new PersonMenu();

        switch (option) {
            case 1 -> {
                LOGGER.log(Level.INFO, "Вызываем меню, где пользователь зарегистрирован");
                personMenu.startMenuIfPersonIsAuthorised();
            }
            case 2 -> {
                LOGGER.log(Level.INFO, "Вызываем меню, где пользователь не зарегистрирован");
                personMenu.startIfPersonIsNotAuthorised();
            }
            case 3 -> {
                LOGGER.log(Level.INFO, "Приложение закончило работу");
                System.out.println("Приложение закончило работу");
                System.exit(0);
            }
        }
    }

    public void printMenu(String[] options) {

        LOGGER.log(Level.INFO, " Начало работы метода по выводу текста меню в консоль");
        LOGGER.log(Level.INFO, "Вывод текста меню в консоль");
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Выберите пункт меню: ");
    }

    public int checkInputtedNumber(int maxMenuOption, String[] menuText) {

        LOGGER.log(Level.INFO, "Начало работы метода по проверке введенного числа пользователем на соответствие меню");
        boolean CorrectValue;
        int result = 0;
        do {
            try {
                CorrectValue = true;
                Scanner scanner = new Scanner(System.in);
                LOGGER.log(Level.WARNING, "Пользователь вводит данные");
                int select2 = scanner.nextInt();

                if (select2 < 1 || select2 > maxMenuOption) {
                    LOGGER.log(Level.WARNING, "Пользователь ввел число, не соответствующее диапазону меню");
                    System.out.println("Вы ввели неверную цифру, попробуйте ещё раз.");
                    CorrectValue = false;
                    printMenu(menuText);
                } else {
                    LOGGER.log(Level.INFO, "Пользовтель ввел верное число");
                    result = select2;
                }
            } catch (InputMismatchException e) {
                LOGGER.log(Level.WARNING, "Пользователь ввел не число");
                CorrectValue = false;
                System.out.println("Вводить необходимо только цифры! \nПопробуйте ещё раз.");
                LOGGER.log(Level.INFO, "Вызов метода по выводу текста меню");
                printMenu(menuText);
            }
        } while (!CorrectValue);
        return result;
    }

    public int countMenuLengthFromMenuArray(String[] menuArray) {

        LOGGER.log(Level.INFO, "Метод расчистывает сколько пунктов у меню");
        return menuArray.length;
    }

}
