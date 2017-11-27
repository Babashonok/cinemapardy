package com.dev.babak.cinemapardy.service

import com.dev.babak.cinemapardy.domain.Cinema
import com.dev.babak.cinemapardy.queue.service.SnsService
import com.dev.babak.cinemapardy.repository.CinemaRepository
import com.dev.babak.cinemapardy.view.CinemaView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CinemaServiceImpl implements  CinemaService{

    @Autowired
    CinemaRepository cinemaRepository

    @Autowired
    CinemaViewService cinemaViewService

    @Autowired
    SnsService snsService

    @Override
    List<CinemaView> getAllCinemas() {
        List<Cinema> CinemasFromDB = cinemaRepository.findAll()
        cinemaViewService.convertDBValuesToJson(CinemasFromDB)
    }

    @Override
    CinemaView getCinemaById(String id) {
        def cinema = cinemaRepository.getOne(id)
        cinemaViewService.convertDBValuesToJson([cinema]).first()
    }

    @Override
    CinemaView uploadCinema(CinemaView cinemaView) {
        Cinema newCinema = cinemaViewService.convertJsonValueToDB(cinemaView)
        cinemaRepository.save(newCinema)
        snsService.pushNotification("new_cinema-docker", cinemaView)
        cinemaView
    }

    @Override
    CinemaView updateCinema(CinemaView cinemaView, String id) {
        Cinema updatedCinema = cinemaViewService.convertJsonValueToDB(cinemaView)
        updatedCinema.id = id
        cinemaRepository.save(updatedCinema)
        cinemaView
    }

    @Override
    void deleteCinema(String id) {
        cinemaRepository.delete(id)
    }
}
