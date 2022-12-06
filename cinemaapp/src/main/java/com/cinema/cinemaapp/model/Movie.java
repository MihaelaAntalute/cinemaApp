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

    @Column
    private Integer price;


    @OneToMany(mappedBy = "movie", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference(value = "movie-projection")
    private List<Projection> projectionList;

    @ManyToOne
    @JoinColumn(name="cinema_room_id")
    @JsonBackReference(value = "cinema-movie")
    private CinemaRoom cinemaRoom;

    public Movie() {
    }

    public Movie(Long id, String movieName, Integer price, List<Projection> projectionList, CinemaRoom cinemaRoom) {
        this.id = id;
        this.movieName = movieName;
        this.price = price;
        this.projectionList = projectionList;
        this.cinemaRoom = cinemaRoom;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
