package com.senla.controller;

public abstract class MenuForAll {

    abstract void startMenu();

    abstract void printMenu(String[] options);

    abstract int checkInputtedNumber(int maxMenuOption, String[] menuText);

    abstract int countMenuLengthFromMenuArray(String[] menuArray);

}
