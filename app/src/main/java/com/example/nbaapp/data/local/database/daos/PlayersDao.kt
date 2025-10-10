package com.example.nbaapp.data.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nbaapp.data.local.database.entities.PlayerEntity
import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.helpers.Result

@Dao
interface PlayersDao {
    @Query("SELECT * FROM players")
    fun getPlayers(): List<PlayerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayers(players: List<PlayerEntity>)
}