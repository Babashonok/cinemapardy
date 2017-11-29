package com.dev.babak.cinemapardy.domain

import javax.persistence.*
import java.time.Instant

@Entity
@Table(name = "cinema")
class Cinema {

    @Id
    @Column(name = "cinema_id", nullable = false)
    String id

    @Column(name = "title", nullable = false)
    String title

    @Column(name = "created_date")
    Instant createdDate

    @Column(name = "director")
    String director

    @Column(name = "imdb_rank", precision = 16, scale = 5)
    BigDecimal imdbRank


}
