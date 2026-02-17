package com.example.nbaapp.data.remote.dtos

import com.squareup.moshi.Json

data class PlayerDto(
    @field:Json(name = "id") val id: Int,

    @field:Json(name = "first_name") val firstName: String,

    @field:Json(name = "last_name") val lastName: String,

    @field:Json(name = "team") val team: TeamDto
)