package com.cinema.cinemaapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String movieName;


    @OneToMany(mappedBy = "movie", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference(value = "movie-projection")
    private List<Projection> projectionList;

    @ManyToOne
    @JoinColumn(name="cinema_room_id")
    @JsonBackReference(value = "cinema-movie")
    private CinemaRoom cinemaRoom;

    public Movie() {
    }


    public Movie(Long id, String movieName, List<Projection> projectionList) {
        this.id = id;
        this.movieName = movieName;
        this.projectionList = projectionList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public List<Projection> getProjectionList() {
        if(this.projectionList == null){
            this.projectionList = new ArrayList<>();
        }
        return projectionList;
    }

    public void setProjectionList(List<Projection> projectionList) {
        this.projectionList = projectionList;
    }

    public CinemaRoom getCinemaRoom() {
        return cinemaRoom;
    }

    public void setCinemaRoom(CinemaRoom cinemaRoom) {
        this.cinemaRoom = cinemaRoom;
    }
}
