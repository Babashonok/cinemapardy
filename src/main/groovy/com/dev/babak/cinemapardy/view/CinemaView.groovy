package com.dev.babak.cinemapardy.view

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class CinemaView {

    @JsonProperty('ID')
    String id

    @JsonProperty('Title')
    String title
}
