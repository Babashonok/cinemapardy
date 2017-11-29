package com.dev.babak.cinemapardy.service

import com.dev.babak.cinemapardy.domain.Cinema
import com.dev.babak.cinemapardy.view.CinemaView
import org.springframework.stereotype.Service

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class CinemaViewServiceImpl implements CinemaViewService{

    @Override
    List<CinemaView> convertDBValuesToJson(List<Cinema> cinemas) {
        List<CinemaView> cinemaViews = new ArrayList<>()
        if (cinemas) {
            cinemas.each {
                CinemaView cinemaView = new CinemaView(
                        id: it.id,
                        title: it.title,
                        createdDate: LocalDateTime.ofInstant(it.createdDate, ZoneId.systemDefault()).toLocalDate(),
                        director: it.director,
                        imdbRank: it.imdbRank

                )
                cinemaViews.add(cinemaView)
            }
        }
        cinemaViews
    }

    @Override
    Cinema convertJsonValueToDB(CinemaView  cinemaView) {
        Cinema cinema = null
        if (cinemaView) {
            cinema = new Cinema(
                    id: cinemaView.id,
                    title: cinemaView.title,
                    createdDate: cinemaView.createdDate.atStartOfDay(ZoneId.systemDefault()).toInstant(),
                    director: cinemaView.director,
                    imdbRank: cinemaView.imdbRank
            )
        }
        cinema
    }
}
