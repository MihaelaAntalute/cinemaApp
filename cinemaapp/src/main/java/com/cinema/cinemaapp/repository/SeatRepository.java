package com.cinema.cinemaapp.repository;

import com.cinema.cinemaapp.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {
 Seat findBySeatRowAndSeatCol(Integer row,Integer col);

}
