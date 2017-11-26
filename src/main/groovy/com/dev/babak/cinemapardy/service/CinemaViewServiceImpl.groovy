package com.dev.babak.cinemapardy.service

import com.dev.babak.cinemapardy.domain.Cinema
import com.dev.babak.cinemapardy.view.CinemaView
import org.springframework.stereotype.Service

@Service
class CinemaViewServiceImpl implements CinemaViewService{

    @Override
    List<CinemaView> convertDBValuesToJson(List<Cinema> cinemas) {
        List<CinemaView> cinemaViews = new ArrayList<>()
        if (cinemas) {
            cinemas.each {
                CinemaView cinemaView = new CinemaView(
                        id: it.id,
                        title: it.title
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
                    title: cinemaView.title
            )
        }
        cinema
    }
}
