package com.example.nbaapp.domain.repository

import com.example.nbaapp.data.local.database.utils.SortTeamBy
import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.helpers.Result
import com.example.nbaapp.domain.models.Team

interface Teams {
    suspend fun getTeams(): Result<List<Team>, DataError>

    suspend fun getTeamsOrdered(sort: SortTeamBy, isAscending: Boolean): Result<List<Team>, DataError.Local>
}