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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeams(teams: List<TeamEntity>)

    @Query("DELETE FROM teams")
    suspend fun deleteAllTeams()

    @RawQuery(observedEntities = [TeamEntity::class])
    suspend fun getTeamsSorted(query: SupportSQLiteQuery): List<TeamEntity>

    @Query("SELECT * FROM teams")
    suspend fun getTeams(): List<TeamEntity>
}