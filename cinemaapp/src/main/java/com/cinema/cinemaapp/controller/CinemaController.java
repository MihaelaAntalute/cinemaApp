package com.cinema.cinemaapp.controller;


import com.cinema.cinemaapp.DTO.AddCinemaRoomDTO;
import com.cinema.cinemaapp.model.CinemaRoom;
import com.cinema.cinemaapp.service.CinemaRoomService;
import com.cinema.cinemaapp.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @GetMapping("/")
    public List<CinemaRoom> getAllCinemas(){
        return cinemaRoomService.getAllCinemas();
    }
    @GetMapping("/{cinemaRoomId}")
    public Optional<CinemaRoom> getCinemaById(@PathVariable Long cinemaRoomId){
        return cinemaRoomService.getCinemaRoomById(cinemaRoomId);
    }
    @PutMapping("/update/{cinemaRoomId}")
    public CinemaRoom updateCinemaRoom(@RequestBody AddCinemaRoomDTO addCinemaRoomDTO,@PathVariable Long cinemaRoomId){
        return cinemaRoomService.updateCinemaRoom(addCinemaRoomDTO,cinemaRoomId);
    }
    @DeleteMapping("/delete/{cinemaRoomId}")
    public void deleteCinemaRoom(@PathVariable Long cinemaRoomId){
        cinemaRoomService.deleteCinemaRoom(cinemaRoomId);
    }






//    @GetMapping("/{movieId}")
//    public Double getTotalPriceTicketsByMovie(@PathVariable Long movieId){
//        return cinemaRoomService.
//
//    }

//    @GetMapping
//    public Double getTotalPriceTicketsByMovie


}
