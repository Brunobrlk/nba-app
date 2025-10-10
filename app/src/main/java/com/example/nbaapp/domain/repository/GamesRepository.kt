package com.example.nbaapp.domain.repository

import com.example.nbaapp.data.remote.RemoteGamesDataSource
import com.example.nbaapp.data.remote.dtos.asDomainList
import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.helpers.Result
import com.example.nbaapp.domain.helpers.map
import com.example.nbaapp.domain.models.Game
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GamesRepository @Inject constructor(
    private val remoteGamesDataSource: RemoteGamesDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : Games {
    override suspend fun getGames(teamId: Int): Result<List<Game>, DataError.Remote> {
        return withContext(ioDispatcher) {
            remoteGamesDataSource.getGames(teamId).map { it.asDomainList() }
        }
    }
}