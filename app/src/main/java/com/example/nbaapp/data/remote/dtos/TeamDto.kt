package com.example.nbaapp.data.remote.dtos

import com.squareup.moshi.Json

data class TeamDto(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "full_name") val name: String,
    @field:Json(name = "city") val city: String,
    @field:Json(name = "conference") val conference: String
)


