package com.dev.babak.cinemapardy.domain

import javax.persistence.*

@Entity
@Table(name = "cinema")
class Cinema {

    @Id
    @Column(name = "cinema_id", nullable = false)
    String id

    @Column(name = "title", nullable = false)
    String title
}
