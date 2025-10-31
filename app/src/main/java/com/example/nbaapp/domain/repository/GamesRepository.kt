package com.example.nbaapp.domain.repository

import com.example.nbaapp.data.mappers.asDomainList
import com.example.nbaapp.data.remote.RemoteGamesDataSource
import com.example.nbaapp.core.helpers.DataError
import com.example.nbaapp.core.helpers.Result
import com.example.nbaapp.core.helpers.map
import com.example.nbaapp.domain.models.Game
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GamesRepository @Inject constructor(
    private val remoteGamesDataSource: RemoteGamesDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : Games {
    override suspend fun getAll(teamId: Int): Result<List<Game>, DataError.Remote> {
        return withContext(ioDispatcher) {
            remoteGamesDataSource.getAll(teamId).map { it.asDomainList() }
        }
    }
}