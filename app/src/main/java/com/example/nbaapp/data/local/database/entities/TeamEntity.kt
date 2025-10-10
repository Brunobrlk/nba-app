package com.example.nbaapp.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nbaapp.domain.models.Team

@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val city: String,
    val conference: String
)

fun TeamEntity.asDomainModel(): Team {
    return Team(
        id = this.id,
        name = this.name,
        city = this.city,
        conference = this.conference
    )
}

fun List<TeamEntity>.asDomainList(): List<Team> {
    return map {
        it.asDomainModel()
    }
}