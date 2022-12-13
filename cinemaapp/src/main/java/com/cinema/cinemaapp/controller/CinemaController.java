package com.cinema.cinemaapp.controller;


import com.cinema.cinemaapp.DTO.AddCinemaRoomDTO;
import com.cinema.cinemaapp.model.CinemaRoom;
import com.cinema.cinemaapp.service.CinemaRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cinema")
public class CinemaController {
    private CinemaRoomService cinemaRoomService;

    @Autowired
    public CinemaController(CinemaRoomService cinemaRoomService) {
        this.cinemaRoomService = cinemaRoomService;
    }

    @PostMapping("/add")
    public CinemaRoom addCinemaRoom(@RequestBody AddCinemaRoomDTO addCinemaRoomDTO) {
        return cinemaRoomService.addCinemaRoom(addCinemaRoomDTO);
    }

    @GetMapping("/")
    public List<CinemaRoom> getCinemaRooms() {
        return cinemaRoomService.getCinemaRooms();
    }

    @PutMapping("/update/{cinemaRoomId}")
    public CinemaRoom updateCinemaRoom(@RequestBody AddCinemaRoomDTO addCinemaRoomDTO, @PathVariable Long cinemaRoomId) {
        return cinemaRoomService.updateCinemaRoom(addCinemaRoomDTO, cinemaRoomId);
    }

    @DeleteMapping("/delete/{cinemaRoomId}")
    public void deleteCinemaRoom(@PathVariable Long cinemaRoomId) {
        cinemaRoomService.deleteCinemaRoom(cinemaRoomId);
    }



}
