package com.example.nbaapp.domain.repository

import com.example.nbaapp.core.helpers.DataError
import com.example.nbaapp.core.helpers.Result
import com.example.nbaapp.data.local.LocalTeamsDataSource
import com.example.nbaapp.data.local.database.utils.SortTeamBy
import com.example.nbaapp.data.mappers.asDomainList
import com.example.nbaapp.data.mappers.asEntityList
import com.example.nbaapp.data.remote.RemoteTeamsDataSource
import com.example.nbaapp.domain.models.Team
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TeamsRepository @Inject constructor(
    private val remoteTeams: RemoteTeamsDataSource,
    private val localTeams: LocalTeamsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : Teams {
    override suspend fun getAll(): Result<List<Team>, DataError> = withContext(ioDispatcher) {
        val teamsDto = remoteTeams.getAll()
        when (teamsDto) {
            is Result.Success -> {
                localTeams.insertAll(teamsDto.data.asEntityList())
                val teams = localTeams.getAll().asDomainList()
                Result.Success(teams)
            }

            is Result.Error -> {
                val cachedTeams = localTeams.getAll()
                Result.Error(teamsDto.error, cachedTeams.asDomainList())
            }
        }
    }

    override suspend fun getAllOrdered(
        sort: SortTeamBy, isAscending: Boolean
    ): Result<List<Team>, DataError.Local> = withContext(ioDispatcher) {
        val teams = localTeams.getAllSorted(sort, isAscending).asDomainList()
        Result.Success(teams)
    }
}