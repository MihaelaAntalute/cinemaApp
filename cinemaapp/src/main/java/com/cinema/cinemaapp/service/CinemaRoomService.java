package com.cinema.cinemaapp.service;

import com.cinema.cinemaapp.DTO.AddCinemaRoomDTO;
import com.cinema.cinemaapp.DTO.ExtraPriceDTO;
import com.cinema.cinemaapp.model.CinemaRoom;
import com.cinema.cinemaapp.model.Movie;
import com.cinema.cinemaapp.model.Seat;
import com.cinema.cinemaapp.repository.CinemaRoomRepository;
import com.cinema.cinemaapp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CinemaRoomService {


    private CinemaRoomRepository cinemaRoomRepository;
    private MovieRepository movieRepository;

    @Autowired
    public CinemaRoomService(CinemaRoomRepository cinemaRoomRepository, MovieRepository movieRepository) {
        this.cinemaRoomRepository = cinemaRoomRepository;
        this.movieRepository = movieRepository;
    }

    public CinemaRoom addCinemaRoom(AddCinemaRoomDTO addCinemaRoomDTO) {
        CinemaRoom cinemaRoom = new CinemaRoom();
        cinemaRoom.setNumberOfRows(addCinemaRoomDTO.getNumberOfRows());
        cinemaRoom.setNumbersOfCols(addCinemaRoomDTO.getNumberOfCols());
        generateSeatsForCinemaRoom(addCinemaRoomDTO, cinemaRoom);
        generateExtraPricesForCinemaRoom(addCinemaRoomDTO, cinemaRoom);
        return cinemaRoomRepository.save(cinemaRoom);

    }

    private void generateExtraPricesForCinemaRoom(AddCinemaRoomDTO addCinemaRoomDTO, CinemaRoom cinemaRoom) {
        //1.parcurgem lista de extraprice-uri
        //2.parcurgem randurile de la startingRow la endingRow,
        // 3.la fiecare loc de pe fiecare rand setam extraprice curent
        for (ExtraPriceDTO extraPriceDTO : addCinemaRoomDTO.getExtraPrices()) {
            for (int i = extraPriceDTO.getStartingRow(); i <= extraPriceDTO.getEndingRow(); i++) {
                for (int j = 0; j < addCinemaRoomDTO.getNumberOfCols(); j++) {
                    Seat seat = getSeatByRowAndCol(cinemaRoom, i, j + 1).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the seat was not found"));
                    seat.setExtraPrice(extraPriceDTO.getExtraPrice());
                }
            }
        }
    }

    private void generateSeatsForCinemaRoom(AddCinemaRoomDTO addCinemaRoomDTO, CinemaRoom cinemaRoom) {
        for (int i = 0; i < addCinemaRoomDTO.getNumberOfRows(); i++) {
            for (int j = 0; j < addCinemaRoomDTO.getNumberOfCols(); j++) {
                Seat seat = new Seat();
                seat.setSeatRow(i + 1);
                seat.setSeatCol(j + 1);
                seat.setExtraPrice(0);
                cinemaRoom.getSeatList().add(seat);
                seat.setCinemaRoom(cinemaRoom);
            }
        }
    }


    public Optional<Seat> getSeatByRowAndCol(CinemaRoom cinemaRoom, Integer row, Integer col) {
//        for (Seat seat : cinemaRoom.getSeatList()) {
//            if (row == seat.getSeatRow() && col == seat.getSeatCol()) {
//                return seat;
//            }
//        }
        //      return null;
        return cinemaRoom.getSeatList().stream()
                .filter((seat -> seat.getSeatRow() == row && seat.getSeatCol() == col))
                .findFirst();
    }

    public List<CinemaRoom> getAllCinemas() {
        return cinemaRoomRepository.findAll();
    }

    public Optional<CinemaRoom> getCinemaRoomById(Long cinemaRoomId) {
        return cinemaRoomRepository.findById(cinemaRoomId);
    }

    public CinemaRoom updateCinemaRoom(AddCinemaRoomDTO addCinemaRoomDTO, Long cinemaRoomId) {
        CinemaRoom foundCinemaRoom = cinemaRoomRepository.findById(cinemaRoomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the cinema was not found"));
        foundCinemaRoom.setNumberOfRows(addCinemaRoomDTO.getNumberOfCols());
        foundCinemaRoom.setNumbersOfCols(addCinemaRoomDTO.getNumberOfCols());
        return cinemaRoomRepository.save(foundCinemaRoom);
    }

    public void deleteCinemaRoom(Long cinemaRoomId) {
        CinemaRoom foundCinemaRoom = cinemaRoomRepository.findById(cinemaRoomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the cinema was not found"));
        cinemaRoomRepository.delete(foundCinemaRoom);
    }
    ////Care este valoarea tuturor biletelor vandute intr-o anumita zi (la un film sau la toate filmele)
//    public Double getTotalPriceTicketsByMovie(Long movieId){
//        Movie foundMovie = movieRepository.findById(movieId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the movie was not found"));
//   Double totalPriceOfAllTickets = 0.0;
//
//


    //1.daca ticketele apartine unui order,sa le adaugam pretul la pretul total
//    foundMovie.getProjectionList().
//    }

    ////Cate bilete s-au vandut la un anumit film sau la toate filmele
}