package com.example.nbaapp.data.remote.dtos

import com.squareup.moshi.Json

data class GameDto(
    @field:Json(name = "id") val id: Int = 0,

    @field:Json(name = "home_team") val homeTeam: TeamDto,

    @field:Json(name = "visitor_team") val visitorTeam: TeamDto,

    @field:Json(name = "home_team_score") val homeTeamScore: Int,

    @field:Json(name = "visitor_team_score") val visitorTeamScore: Int
)
