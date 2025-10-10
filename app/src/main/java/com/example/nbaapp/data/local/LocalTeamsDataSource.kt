package com.example.nbaapp.data.local

import com.example.nbaapp.data.local.database.entities.TeamEntity
import com.example.nbaapp.data.local.database.utils.SortTeamBy
import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.helpers.Result

interface LocalTeamsDataSource {
    suspend fun insertTeams(teams: List<TeamEntity>): Result<Unit, DataError.Local>
    suspend fun getTeams(): Result<List<TeamEntity>, DataError.Local>
    suspend fun getTeamsSorted(sort: SortTeamBy, isAscending: Boolean): Result<List<TeamEntity>, DataError.Local>
}