package com.example.nbaapp.data.remote.dtos

import com.squareup.moshi.Json

data class TeamDto(
    @Json(name = "id") val id: Int,
    @Json(name = "full_name") val name: String,
    @Json(name = "city") val city: String,
    @Json(name = "conference") val conference: String
)


