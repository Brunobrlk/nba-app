package com.example.nbaapp.data.local

import com.example.nbaapp.data.local.database.entities.TeamEntity
import com.example.nbaapp.data.local.database.utils.SortTeamBy
import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.helpers.Result

interface LocalTeamsDataSource {
    suspend fun insertAll(teams: List<TeamEntity>)
    suspend fun getAll(): List<TeamEntity>
    suspend fun getAllSorted(sort: SortTeamBy, isAscending: Boolean): List<TeamEntity>
}