package com.dev.babak.cinemapardy.controller

import com.dev.babak.cinemapardy.service.CinemaService
import com.dev.babak.cinemapardy.view.CinemaView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

@RequestMapping('/cinema')
@RestController
class CinemaController {

    @Autowired
     CinemaService cinemaService

    @RequestMapping(value = "/get-all", method = RequestMethod.GET)
    ResponseEntity<List<CinemaView>> getDataExportFeeds() {
        return ResponseEntity.ok(cinemaService.getAllCinemas())
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)

    ResponseEntity<CinemaView> getDataExportFeed(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(cinemaService.getCinemaById(id))
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    ResponseEntity<CinemaView> createDataExportFeed(
            @Valid @RequestBody CinemaView cinemaView
            ) {
        return new ResponseEntity(cinemaService.uploadCinema(cinemaView), HttpStatus.CREATED)
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)

    ResponseEntity<CinemaView> updateDataExportFeed(
            @PathVariable('id') String id,
            @Valid @RequestBody CinemaView cinemaView
            ) {
        return  ResponseEntity.ok(cinemaService.updateCinema(cinemaView, id))
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    ResponseEntity deleteDataExportFeed(@PathVariable('id') String id) {
        cinemaService.deleteCinema(id)
        return ResponseEntity.ok().build()
    }
}



