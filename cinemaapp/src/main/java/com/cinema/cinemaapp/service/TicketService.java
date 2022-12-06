package com.cinema.cinemaapp.service;

import com.cinema.cinemaapp.model.CinemaRoom;
import com.cinema.cinemaapp.model.Movie;
import com.cinema.cinemaapp.model.Seat;
import com.cinema.cinemaapp.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    private TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

//Care este valoarea tuturor biletelor vandute intr-o anumita zi (la un film sau la toate filmele)
//Cate bilete s-au vandut la un anumit film sau la toate filmele

}
