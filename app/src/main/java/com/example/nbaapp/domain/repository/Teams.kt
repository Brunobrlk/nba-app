package com.example.nbaapp.domain.repository

import com.example.nbaapp.data.local.database.utils.SortTeamBy
import com.example.nbaapp.core.helpers.DataError
import com.example.nbaapp.core.helpers.Result
import com.example.nbaapp.domain.models.Team

interface Teams {
    suspend fun getAll(): Result<List<Team>, DataError>

    suspend fun getAllOrdered(sort: SortTeamBy, isAscending: Boolean): Result<List<Team>, DataError.Local>
}