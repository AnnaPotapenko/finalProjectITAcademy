package com.senla.repository;

import com.senla.model.Ticket;

import java.util.List;

public interface TicketRepository extends Repository<Ticket> {


    Ticket createTicketWithFilmID(Ticket ticket, int filmID);

    List<Ticket> getAllTicket();

    List<Ticket> getAllTicketUsingFilmID(int filmID);

    List<Ticket> getNotSoldTicketList(int filmID);

    void addUserIDtoTicket(int userID, int filmId);

    List<Ticket> getTicketWithUserID(int userID);

    List<Integer> getTicketIDListWithUserLogin(String myLogin);

    int getTicketPrice(int userInputtedTicketID);

    void updateReturnedTicketInfo(int userInputtedTicketID);

    void deleteAllTicket(int filmIdToDelete);

    void deleteAllUserTicket(int userIdToDelete);

    String getTicketDataTime(int userInputtedTicketID);
}
