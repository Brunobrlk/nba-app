package com.example.nbaapp.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nbaapp.domain.models.Player

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val teamName: String,
    val teamId: Int
)

fun PlayerEntity.asDomainModel(): Player {
    return Player(
        firstName = this.firstName,
        lastName = this.lastName,
        teamName = this.teamName,
        teamId = this.teamId
    )
}

fun List<PlayerEntity>.asDomainList(): List<Player> {
    return map {
        it.asDomainModel()
    }
}