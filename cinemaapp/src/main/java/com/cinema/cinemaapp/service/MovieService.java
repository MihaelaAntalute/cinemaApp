package com.cinema.cinemaapp.service;
import com.cinema.cinemaapp.DTO.AddMovieDTO;
import com.cinema.cinemaapp.DTO.ProjectionsDTO;
import com.cinema.cinemaapp.model.*;
import com.cinema.cinemaapp.repository.CinemaRoomRepository;
import com.cinema.cinemaapp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private MovieRepository movieRepository;
    private CinemaRoomRepository cinemaRoomRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, CinemaRoomRepository cinemaRoomRepository) {
        this.movieRepository = movieRepository;
        this.cinemaRoomRepository = cinemaRoomRepository;
    }

    public Movie addMovie(AddMovieDTO addMovieDTO) {
        CinemaRoom foundCinemaRoom = cinemaRoomRepository.findById(addMovieDTO.getCinemaRoomId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the cinema was not found"));
        Movie movieToBeAdd = new Movie();
        movieToBeAdd.setMovieName(addMovieDTO.getMovieName());
        movieToBeAdd.setPrice(addMovieDTO.getPrice());
        movieToBeAdd.setCinemaRoom(foundCinemaRoom);
        Optional<Movie> foundMovie = movieRepository.findByMovieName(movieToBeAdd.getMovieName());
        if(foundMovie.isPresent()){
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, "This movie name already exist");
        }
        generateProjections(addMovieDTO, foundCinemaRoom, movieToBeAdd);
        return movieRepository.save(movieToBeAdd);
    }


    //1.parcurgem lista de filme,parcurgem lista de proiectie
    //2.parcurgem lista de proiectii si luam de la fiecare proiectie startTime si endTime
    //3.daca startTime sau endTime sunt egale cu startTime sau endTime de la alt film adaugat,aruncam exceptie
    private void generateProjections(AddMovieDTO addMovieDTO, CinemaRoom foundCinemaRoom, Movie movieToBeAdd) {
        addMovieDTO.getDates().forEach(projectionsDTO -> {
            Optional<Projection> interfiringProjection = canProjectionBeAdded(foundCinemaRoom, projectionsDTO);
            if (interfiringProjection.isPresent()){
                throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, "there is already a projection between following dates"+ " " + interfiringProjection.get().getStartTime()+" "+interfiringProjection.get().getEndTime());
            }
            Projection projection = new Projection();
            projection.setStartTime(projectionsDTO.getStartTime());
            projection.setEndTime(projectionsDTO.getEndTime());
            projection.setMovie(movieToBeAdd);
            movieToBeAdd.getProjectionList().add(projection);
            generateTicketsForProjection(foundCinemaRoom, projection);

        });
    }

    private Optional<Projection> canProjectionBeAdded(CinemaRoom foundCinemaRoom, ProjectionsDTO projection) {
        for (Movie movie : foundCinemaRoom.getMovieList()) {
            for (Projection existingProjection : movie.getProjectionList()) {
                if (!(projection.getEndTime().isBefore(existingProjection.getStartTime()) || projection.getStartTime().isAfter(existingProjection.getEndTime()))) {
                    return Optional.of(existingProjection);
                }
            }
        }
        return Optional.empty();
    }


    private void generateTicketsForProjection(CinemaRoom foundCinemaRoom, Projection projection) {
        for (Seat seat : foundCinemaRoom.getSeatList()) {
            Ticket ticket = new Ticket();
            ticket.setAvailable(true);
            ticket.setProjection(projection);
            projection.getTicketList().add(ticket);
            ticket.setSeat(seat);
        }
    }

    //Vad lista de locuri disponibile la un film
    //Hint - asta numai daca filmul are date la care se va difuza in viitor
    public List<Projection> getAllProjectionsAvailable(Long movieId) {
        Movie foundMovie = movieRepository.findById(movieId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the movie was not found"));
//        List<Projection> projectionsAvailable = new ArrayList<>();
//        for (Projection projection : foundMovie.getProjectionList()) {
//            if (projection.getStartTime().isAfter(LocalDateTime.now())) {
//                boolean hasProjectionAvailableTickets = projection.getTicketList().stream()
//                        .anyMatch(ticket -> ticket.getAvailable().equals(true));
//                if (hasProjectionAvailableTickets){
//                    projectionsAvailable.add(projection);
//                }
//            }
//        }
//        return projectionsAvailable;

        return foundMovie.getProjectionList().stream()
                .filter(projection -> projection.getStartTime().isAfter(LocalDateTime.now()))
                .filter(projection -> hasProjectionAvailableTickets(projection))
                .collect(Collectors.toList());
    }

    public boolean hasProjectionAvailableTickets(Projection projection){
        return projection.getTicketList().stream()
                .anyMatch(ticket -> ticket.getAvailable());
    }


}

//Care este valoarea tuturor biletelor vandute intr-o anumita zi (la un film sau la toate filmele)
//Cate bilete s-au vandut la un anumit film sau la toate filmele


//Cumpar un bilet la un film
//Pretul pentru fiecare loc se va calcula in functie de pretul firmului, la care se va adauga un extraPrice daca acel loc are extraPrice


