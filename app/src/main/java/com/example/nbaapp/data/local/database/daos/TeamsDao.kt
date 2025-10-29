package com.example.nbaapp.data.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.nbaapp.data.local.database.entities.TeamEntity

@Dao
interface TeamsDao {
    @Query("SELECT * FROM teams")
    suspend fun getAll(): List<TeamEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(teams: List<TeamEntity>)

    @Query("DELETE FROM teams")
    suspend fun clear()

    @RawQuery(observedEntities = [TeamEntity::class])
    suspend fun getAllSorted(query: SupportSQLiteQuery): List<TeamEntity>
}