package com.example.nbaapp.data.local

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.nbaapp.data.local.database.daos.TeamsDao
import com.example.nbaapp.data.local.database.entities.TeamEntity
import com.example.nbaapp.data.local.database.utils.SortTeamBy
import javax.inject.Inject

class RoomTeamsDataSource @Inject constructor(private val teamsDao: TeamsDao) :
    LocalTeamsDataSource {
    override suspend fun getAllSorted(
        sort: SortTeamBy,
        isAscending: Boolean
    ): List<TeamEntity> {
        val column = when (sort) {
            SortTeamBy.NAME -> "name"
            SortTeamBy.CITY -> "city"
            SortTeamBy.CONFERENCE -> "conference"
        }

        val order = if (isAscending) "ASC" else "DESC"
        val query = SimpleSQLiteQuery("SELECT * FROM teams ORDER BY $column $order")
        return teamsDao.getAllSorted(query)
    }

    override suspend fun insertAll(teams: List<TeamEntity>) = teamsDao.insertAll(teams)

    override suspend fun getAll() = teamsDao.getAll()
}