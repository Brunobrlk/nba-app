package com.example.nbaapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nbaapp.data.local.database.daos.PlayersDao
import com.example.nbaapp.data.local.database.daos.RemoteKeysDao
import com.example.nbaapp.data.local.database.daos.TeamsDao
import com.example.nbaapp.data.local.database.entities.PlayerEntity
import com.example.nbaapp.data.local.database.entities.RemoteKeyEntity
import com.example.nbaapp.data.local.database.entities.TeamEntity

@Database(
    entities = [PlayerEntity::class, TeamEntity::class, RemoteKeyEntity::class], version = 1, exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun getPlayersDao(): PlayersDao
    abstract fun getTeamsDao(): TeamsDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}