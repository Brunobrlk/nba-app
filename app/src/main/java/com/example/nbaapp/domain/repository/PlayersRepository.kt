package com.example.nbaapp.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.example.nbaapp.data.local.database.entities.PlayerEntity
import com.example.nbaapp.data.mappers.asDomainList
import com.example.nbaapp.data.mappers.asDomainModel
import com.example.nbaapp.data.remote.RemotePlayersDataSource
import com.example.nbaapp.core.helpers.DataError
import com.example.nbaapp.core.helpers.Result
import com.example.nbaapp.core.helpers.map
import com.example.nbaapp.domain.models.Player
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlayersRepository @Inject constructor(
    private val remotePlayers: RemotePlayersDataSource,
    private val pager: Pager<Int, PlayerEntity>,
    private val ioDispatcher: CoroutineDispatcher
) : Players {

    override suspend fun search(name: String): Result<List<Player>, DataError.Remote> {
        return withContext(ioDispatcher) {
            remotePlayers.search(name).map { it.asDomainList() }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPlayersPaging(): Flow<PagingData<Player>> {
        return pager.flow
            .flowOn(ioDispatcher)
            .map { pagingData ->
                pagingData.map { entity ->
                    entity.asDomainModel()
                }
            }
    }
}