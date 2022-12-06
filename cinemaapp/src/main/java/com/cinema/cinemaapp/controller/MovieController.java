package com.cinema.cinemaapp.controller;

import com.cinema.cinemaapp.DTO.AddMovieDTO;
import com.cinema.cinemaapp.model.Movie;
import com.cinema.cinemaapp.model.Projection;
import com.cinema.cinemaapp.model.Seat;
import com.cinema.cinemaapp.repository.MovieRepository;
import com.cinema.cinemaapp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add")
    public Movie addMovie(@RequestBody AddMovieDTO addMovieDTO) {
        return movieService.addMovie(addMovieDTO);
    }

    @GetMapping("/projections-available/{movieId}")
    public List<Projection> getAllProjectionsAvailable(@PathVariable Long movieId) {
        return movieService.getAllProjectionsAvailable(movieId);
    }



}
