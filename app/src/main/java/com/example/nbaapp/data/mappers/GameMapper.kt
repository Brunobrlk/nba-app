package com.example.nbaapp.data.mappers

import com.example.nbaapp.data.remote.dtos.ApiDataDto
import com.example.nbaapp.data.remote.dtos.GameDto
import com.example.nbaapp.domain.models.Game

// --- Map to Domain
fun GameDto.asDomainModel() = Game(
    homeName = this.homeTeam.name,
    homeScore = this.homeTeamScore,
    visitorName = this.visitorTeam.name,
    visitorScore = this.visitorTeamScore
)

fun ApiDataDto<List<GameDto>>.asDomainList() = this.data.map { it.asDomainModel() }
