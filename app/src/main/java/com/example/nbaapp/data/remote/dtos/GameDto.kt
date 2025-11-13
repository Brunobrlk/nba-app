package com.example.nbaapp.data.remote.dtos

import com.squareup.moshi.Json

data class GameDto(
    @Json(name = "id") val id: Int = 0,

    @Json(name = "home_team") val homeTeam: TeamDto,

    @Json(name = "visitor_team") val visitorTeam: TeamDto,

    @Json(name = "home_team_score") val homeTeamScore: Int,

    @Json(name = "visitor_team_score") val visitorTeamScore: Int
)
