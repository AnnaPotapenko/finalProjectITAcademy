package com.senla;
import com.senla.controller.MainCinemaMenu;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        LOGGER.log(Level.INFO, "Начало работы mein, запускаем меню кинотеатра");
        MainCinemaMenu.startCinemaMenu();
    }
}
