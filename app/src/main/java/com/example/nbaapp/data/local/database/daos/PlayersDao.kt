package com.example.nbaapp.data.local.database.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nbaapp.data.local.database.entities.PlayerEntity

@Dao
interface PlayersDao {
    @Query("SELECT * FROM players")
    suspend fun getAll(): List<PlayerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(players: List<PlayerEntity>)

    @Query("DELETE FROM players")
    fun clear()

    @Query("SELECT * FROM players")
    fun pagingSource(): PagingSource<Int, PlayerEntity>
}