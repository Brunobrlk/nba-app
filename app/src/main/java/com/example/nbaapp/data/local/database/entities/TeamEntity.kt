package com.example.nbaapp.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val city: String,
    val conference: String
)
