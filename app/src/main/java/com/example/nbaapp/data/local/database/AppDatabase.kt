package com.example.nbaapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nbaapp.data.local.database.entities.PlayerEntity
import com.example.nbaapp.data.local.database.entities.TeamEntity
import com.example.nbaapp.data.local.database.daos.PlayersDao
import com.example.nbaapp.data.local.database.daos.TeamsDao

@Database(
    entities = [PlayerEntity::class, TeamEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun getPlayerDao(): PlayersDao
    abstract fun getTeamDao(): TeamsDao
}