package com.example.nbaapp.data.mappers

import com.example.nbaapp.data.local.database.entities.TeamEntity
import com.example.nbaapp.data.remote.dtos.ApiDataDto
import com.example.nbaapp.data.remote.dtos.TeamDto
import com.example.nbaapp.domain.models.Team


// --- Dto to Domain
fun TeamDto.asDomainModel() = Team(
    id = this.id,
    name = normalizeBlank(this.name),
    city = normalizeBlank(this.city),
    conference = normalizeBlank(this.conference)
)

fun ApiDataDto<List<TeamDto>>.asDomainList() = this.data.map { it.asDomainModel() }

// --- Dto to Entity
fun TeamDto.asEntityModel() = TeamEntity(
    id = this.id,
    name = this.name,
    city = this.city,
    conference = this.conference
)

fun ApiDataDto<List<TeamDto>>.asEntityList() = this.data.map { it.asEntityModel() }

// --- Entity to Domain
fun TeamEntity.asDomainModel() = Team(
    id = this.id,
    name = normalizeBlank(this.name),
    city = normalizeBlank(this.city),
    conference = normalizeBlank(this.conference)
)

fun List<TeamEntity>.asDomainList() = map { it.asDomainModel() }