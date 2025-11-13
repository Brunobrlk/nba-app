package com.example.nbaapp.data.remote.dtos

import com.squareup.moshi.Json

data class PlayerDto(
    @Json(name = "id") val id: Int,

    @Json(name = "first_name") val firstName: String,

    @Json(name = "last_name") val lastName: String,

    @Json(name = "team") val team: TeamDto
)