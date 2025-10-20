package com.example.nbaapp.data.local

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.nbaapp.data.local.database.daos.TeamsDao
import com.example.nbaapp.data.local.database.entities.TeamEntity
import com.example.nbaapp.data.local.database.utils.SortTeamBy
import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.helpers.Result
import javax.inject.Inject

class RoomTeamsDataSource @Inject constructor(private val teamsDao: TeamsDao) : LocalTeamsDataSource {
    override suspend fun getTeamsSorted(
        sort: SortTeamBy,
        isAscending: Boolean
    ): Result<List<TeamEntity>, DataError.Local> {
        return try {
            val column = when (sort) {
                SortTeamBy.NAME -> "name"
                SortTeamBy.CITY -> "city"
                SortTeamBy.CONFERENCE -> "conference"
            }

            val order = if (isAscending) "ASC" else "DESC"
            val query = SimpleSQLiteQuery("SELECT * FROM teams ORDER BY $column $order")
            val teams = teamsDao.getTeamsSorted(query)
            Result.Success(teams)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun insertTeams(teams: List<TeamEntity>): Result<Unit, DataError.Local> {
        return try {
            teamsDao.insertTeams(teams)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun getTeams(): Result<List<TeamEntity>, DataError.Local> {
        return try {
            val teams = teamsDao.getTeams()
            Result.Success(teams)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }

    }
}