package com.dev.babak.cinemapardy.service

import com.dev.babak.cinemapardy.domain.Cinema
import com.dev.babak.cinemapardy.view.CinemaView

interface CinemaViewService {
    List<CinemaView> convertDBValuesToJson(List<Cinema> cinemas)
    Cinema convertJsonValueToDB(CinemaView cinemaView)

}