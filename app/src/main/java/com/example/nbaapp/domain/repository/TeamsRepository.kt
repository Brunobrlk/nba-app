package com.example.nbaapp.domain.repository

import com.example.nbaapp.data.local.LocalTeamsDataSource
import com.example.nbaapp.data.local.database.entities.asDomainList
import com.example.nbaapp.data.local.database.utils.SortTeamBy
import com.example.nbaapp.data.remote.RemoteTeamsDataSource
import com.example.nbaapp.data.remote.dtos.asEntityList
import com.example.nbaapp.data.remote.dtos.asDomainList
import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.models.Team
import com.example.nbaapp.domain.helpers.Result
import com.example.nbaapp.domain.helpers.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TeamsRepository @Inject constructor(
    private val remoteTeams: RemoteTeamsDataSource,
    private val localTeams: LocalTeamsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : Teams {
    override suspend fun getTeams(): Result<List<Team>, DataError> {
        return withContext(ioDispatcher) {
            val teamsDto = remoteTeams.getTeams()
            when(teamsDto){
                is Result.Success -> {
                    localTeams.insertTeams(teamsDto.data.asEntityList() )
                    localTeams.getTeams().map { it.asDomainList() }
                }
                is Result.Error -> {
                    val cachedTeams = localTeams.getTeams()
                    if (cachedTeams is Result.Success) {
                        Result.Error(
                            teamsDto.error,
                            cachedTeams.data.asDomainList()
                        )
                    } else {
                        Result.Error(teamsDto.error)
                    }
                }
            }
        }
    }

    override suspend fun getTeamsOrdered(
        sort: SortTeamBy,
        isAscending: Boolean
    ): Result<List<Team>, DataError.Local> {
        return withContext(ioDispatcher) {
            localTeams.getTeamsSorted(sort, isAscending).map { it.asDomainList() }
        }
    }
}