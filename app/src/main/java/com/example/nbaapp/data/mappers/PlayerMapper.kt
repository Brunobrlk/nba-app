package com.example.nbaapp.data.mappers

import com.example.nbaapp.data.local.database.entities.PlayerEntity
import com.example.nbaapp.data.remote.dtos.ApiDataDto
import com.example.nbaapp.data.remote.dtos.PlayerDto
import com.example.nbaapp.domain.models.Player

// --- Dto to Domain
fun PlayerDto.asDomainModel() = Player(
    firstName = this.firstName,
    lastName = this.lastName,
    teamName = this.team.name,
    teamId = this.team.id
)

fun ApiDataDto<List<PlayerDto>>.asDomainList() = this.data.map { it.asDomainModel() }

// Dto to Entity
fun PlayerDto.asEntityModel() = PlayerEntity(
    id = this.id,
    firstName = this.firstName,
    lastName = this.lastName,
    teamId = this.team.id,
    teamName = this.team.name
)

fun ApiDataDto<List<PlayerDto>>.asEntityList() = this.data.map { it.asEntityModel() }

// --- Entity to Domain
fun PlayerEntity.asDomainModel() = Player(
    firstName = this.firstName,
    lastName = this.lastName,
    teamName = this.teamName,
    teamId = this.teamId
)

fun List<PlayerEntity>.asDomainList() = map { it.asDomainModel() }