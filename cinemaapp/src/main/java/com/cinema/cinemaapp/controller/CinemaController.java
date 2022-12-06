package com.cinema.cinemaapp.controller;


import com.cinema.cinemaapp.DTO.AddCinemaRoomDTO;
import com.cinema.cinemaapp.model.CinemaRoom;
import com.cinema.cinemaapp.service.CinemaRoomService;
import com.cinema.cinemaapp.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cinema")
public class CinemaController {
    private CinemaRoomService cinemaRoomService;
    private TicketService ticketService;


    @Autowired
    public CinemaController(CinemaRoomService cinemaRoomService,TicketService ticketService) {
        this.cinemaRoomService = cinemaRoomService;
        this.ticketService = ticketService;
    }

    @PostMapping("/add")
    public CinemaRoom addCinemaRoom(@RequestBody AddCinemaRoomDTO addCinemaRoomDTO) {
        return cinemaRoomService.addCinemaRoom(addCinemaRoomDTO);
    }

//Care este valoarea tuturor biletelor vandute intr-o anumita zi (la un film sau la toate filmele)
//Cate bilete s-au vandut la un anumit film sau la toate filmele


}
