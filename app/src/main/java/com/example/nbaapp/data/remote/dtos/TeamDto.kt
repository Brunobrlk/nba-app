package com.example.nbaapp.data.remote.dtos

import com.example.nbaapp.data.local.database.entities.TeamEntity
import com.example.nbaapp.domain.models.Team
import com.google.gson.annotations.SerializedName

data class TeamDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("city")
    val city: String,

    @SerializedName("conference")
    val conference: String
)

fun TeamDto.asDomainModel(): Team {
    return Team(
        id = this.id,
        name = this.fullName,
        city = this.city,
        conference = this.conference
    )
}

fun List<TeamDto>.asDomainList(): List<Team> {
    return map {
        it.asDomainModel()
    }
}

fun TeamDto.asEntityModel(): TeamEntity {
    return TeamEntity(
        id = this.id,
        name = this.fullName,
        city = this.city,
        conference = this.conference
    )
}

fun List<TeamDto>.asEntityList(): List<TeamEntity> {
    return map {
        it.asEntityModel()
    }
}
