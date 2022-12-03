package com.cinema.cinemaapp.controller;

import com.cinema.cinemaapp.DTO.AuthDTO;
import com.cinema.cinemaapp.DTO.RegisterDTO;
import com.cinema.cinemaapp.model.User;
import com.cinema.cinemaapp.service.JwtTokenService;
import com.cinema.cinemaapp.service.UserDetailsServiceImpl;
import com.cinema.cinemaapp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody AuthDTO user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        return jwtTokenService.generateToken(userDetails);
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterDTO newUser) {
        return userService.register(newUser);
    }
}
