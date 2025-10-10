package com.example.nbaapp.data.remote.dtos

import com.example.nbaapp.domain.models.Game
import com.google.gson.annotations.SerializedName

data class GameDto (
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("home_team")
    val homeTeam: TeamDto,

    @SerializedName("visitor_team")
    val visitorTeam: TeamDto,

    @SerializedName("home_team_score")
    val homeTeamScore: Int,

    @SerializedName("visitor_team_score")
    val visitorTeamScore: Int
)

fun GameDto.asDomainModel(): Game {
    return Game(
        homeName = this.homeTeam.fullName,
        homeScore = this.homeTeamScore,
        visitorName = this.visitorTeam.fullName,
        visitorScore = this.visitorTeamScore
    )
}

fun List<GameDto>.asDomainList(): List<Game> {
    return map {
        it.asDomainModel()
    }
}