package co.lucaspinazzola.example.domain.model

import co.lucaspinazzola.example.domain.utils.Date

data class Gif(
    val id: String,
    val url: String,
    val urlWebp: String,
    val trendingDatetime: Date
)