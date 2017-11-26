package com.dev.babak.cinemapardy.repository

import com.dev.babak.cinemapardy.domain.Cinema
import org.springframework.data.jpa.repository.JpaRepository

interface CinemaRepository extends JpaRepository<Cinema, String> {

}