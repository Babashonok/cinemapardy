package com.dev.babak.cinemapardy.view

import com.fasterxml.jackson.annotation.JsonFormat
import groovy.transform.EqualsAndHashCode

import java.time.LocalDate

@EqualsAndHashCode
class CinemaView {

    String id
    String title
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    LocalDate createdDate
    String director
    BigDecimal imdbRank
}
