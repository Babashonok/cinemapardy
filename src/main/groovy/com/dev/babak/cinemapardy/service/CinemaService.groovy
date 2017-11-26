package com.dev.babak.cinemapardy.service

import com.dev.babak.cinemapardy.view.CinemaView

interface CinemaService {

    List<CinemaView> getAllCinemas()

    CinemaView getCinemaById(String id)

    CinemaView uploadCinema(CinemaView cinemaView)

    CinemaView updateCinema(CinemaView cinemaView, String id)

    void deleteCinema(String id)
}