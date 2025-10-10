package com.example.nbaapp.data.remote.dtos

import com.example.nbaapp.data.local.database.entities.PlayerEntity
import com.example.nbaapp.domain.models.Player
import com.google.gson.annotations.SerializedName

data class PlayerDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    @SerializedName("team")
    val team: TeamDto
)

fun PlayerDto.asDomainModel(): Player {
    return Player(
        firstName = this.firstName,
        lastName = this.lastName,
        teamName = this.team.fullName,
        teamId = this.team.id
    )
}

fun PlayerDto.asEntityModel(): PlayerEntity {
    return PlayerEntity(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        teamId = this.team.id,
        teamName = this.team.fullName
    )
}

fun List<PlayerDto>.asDomainList(): List<Player> {
    return map {
        it.asDomainModel()
    }
}

fun List<PlayerDto>.asEntityList(): List<PlayerEntity> {
    return map {
        it.asEntityModel()
    }
}