package com.senla.model;

import java.util.Objects;

public class Ticket {
    private int ticketID;
    private String ticketFilmName;
    private String ticketStatus;
    private String ticketPrice;
    private String ticketFilmTime;

    public Ticket() {
    }

    public Ticket(int ticketID, String ticketFilmName, String ticketStatus, String ticketPrice, String ticketFilmTime) {
        this.ticketID = ticketID;
        this.ticketFilmName = ticketFilmName;
        this.ticketStatus = ticketStatus;
        this.ticketPrice = ticketPrice;
        this.ticketFilmTime = ticketFilmTime;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getTicketFilmName() {
        return ticketFilmName;
    }

    public void setTicketFilmName(String ticketFilmName) {
        this.ticketFilmName = ticketFilmName;
    }

    public String getTicketFilmTime() {
        return ticketFilmTime;
    }

    public void setTicketFilmTime(String ticketFilmTime) {
        this.ticketFilmTime = ticketFilmTime;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketID=" + ticketID +
                ", ticketFilmName='" + ticketFilmName + '\'' +
                ", ticketStatus='" + ticketStatus + '\'' +
                ", ticketPrice='" + ticketPrice + '\'' +
                ", ticketFilmTime='" + ticketFilmTime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return ticketID == ticket.ticketID && Objects.equals(ticketFilmName, ticket.ticketFilmName) && Objects.equals(ticketStatus, ticket.ticketStatus) && Objects.equals(ticketPrice, ticket.ticketPrice) && Objects.equals(ticketFilmTime, ticket.ticketFilmTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketID, ticketFilmName, ticketStatus, ticketPrice, ticketFilmTime);
    }
}

