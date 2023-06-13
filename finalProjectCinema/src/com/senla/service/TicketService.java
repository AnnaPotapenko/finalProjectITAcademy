package com.senla.service;

import com.senla.model.Ticket;

import java.util.List;

public interface TicketService {

    Ticket createTicketWithFilmID(Ticket ticket, int filmID);

    List<Ticket> getAllTickets();

    List<Ticket> getAllTicketUsingFilmID(int filmID);

    List<Ticket> getNotSoldTicketList(int filmId);

    void addUserIDToTicketWithFilmID(int userID, int filmId);

    List<Ticket> getTicketUsingUserID(int userID);

    List<Integer> getTicketIDListWithUserLogin(String myLogin);

    int getTicketPrice(int userInputtedTicketID);

    void updateReturnedTicketInfo(int userInputtedTicketID);

    void deleteAllTicket(int filmIdToDelete);

    void deleteAllUserTicket(int userIdToDelete);

    String getTicketDataTime(int userInputtedTicketID);
}
