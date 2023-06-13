package com.senla.service;

import com.senla.Main;
import com.senla.model.Ticket;
import com.senla.repository.TicketRepository;
import com.senla.repository.TicketRepositoryImpl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketServiceImpl implements TicketService {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private final TicketRepository ticketRepository = new TicketRepositoryImpl();


    @Override
    public Ticket createTicketWithFilmID(Ticket ticket, int filmID) {

        LOGGER.log(Level.INFO, "Идем в БД создавать билеты");
        return getTicketRepository().createTicketWithFilmID(ticket, filmID);
    }

    @Override
    public List<Ticket> getAllTickets() {

        LOGGER.log(Level.INFO, "Идем в БД доставать список билетов");
        return getTicketRepository().getAllTicket();
    }

    @Override
    public List<Ticket> getAllTicketUsingFilmID(int filmID) {

        LOGGER.log(Level.INFO, "Идем в БД для получения списка билетов фильма");
        return getTicketRepository().getAllTicketUsingFilmID(filmID);
    }

    @Override
    public List<Ticket> getNotSoldTicketList(int filmId) {

        LOGGER.log(Level.INFO, "Идем в БД, достаем все доступные к продаже билеты");
        return getTicketRepository().getNotSoldTicketList(filmId);
    }

    @Override
    public void addUserIDToTicketWithFilmID(int userID, int filmId) {

        LOGGER.log(Level.INFO, "Идем в БД, добавляем к билету айди пользователя");
        getTicketRepository().addUserIDtoTicket(userID, filmId);
    }

    @Override
    public List<Ticket> getTicketUsingUserID(int userID) {

        LOGGER.log(Level.INFO, "Идем в БД, достаем список билетов у пользователя по его айди");
        return getTicketRepository().getTicketWithUserID(userID);
    }

    @Override
    public List<Integer> getTicketIDListWithUserLogin(String myLogin) {

        LOGGER.log(Level.INFO, "Идет в БД, достаем список айди билетов у пользователя по его логину");
        return getTicketRepository().getTicketIDListWithUserLogin(myLogin);
    }

    @Override
    public int getTicketPrice(int userInputtedTicketID) {

        LOGGER.log(Level.INFO, "Идем в БД за ценой билета");
        return getTicketRepository().getTicketPrice(userInputtedTicketID);
    }

    @Override
    public void updateReturnedTicketInfo(int userInputtedTicketID) {

        LOGGER.log(Level.INFO, "Идем в БД изменять билет");
        getTicketRepository().updateReturnedTicketInfo(userInputtedTicketID);
    }

    @Override
    public void deleteAllTicket(int filmIdToDelete) {

        LOGGER.log(Level.INFO, "Идем в БД для удаления билетов фильма");
        getTicketRepository().deleteAllTicket(filmIdToDelete);
    }

    @Override
    public void deleteAllUserTicket(int userIdToDelete) {

        LOGGER.log(Level.INFO, "Идем в БД билета для удаления билета пользователя");
        getTicketRepository().deleteAllUserTicket(userIdToDelete);
    }

    @Override
    public String getTicketDataTime(int userInputtedTicketID) {

        LOGGER.log(Level.INFO, "Идем в БД, достаем дату-время билета к возврату");
        return getTicketRepository().getTicketDataTime(userInputtedTicketID);
    }

    public TicketRepository getTicketRepository() {
        return ticketRepository;
    }
}
