package com.example.nbaapp.data.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.nbaapp.data.local.LocalPlayersDataSource
import com.example.nbaapp.data.local.database.entities.PlayerEntity
import com.example.nbaapp.data.mappers.asEntityList
import com.example.nbaapp.data.remote.RemotePlayersDataSource
import com.example.nbaapp.domain.helpers.getOrElse
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PlayersRemoteMediator @Inject constructor(
    private val remotePlayers: RemotePlayersDataSource,
    private val localPlayers: LocalPlayersDataSource,
) : RemoteMediator<Int, PlayerEntity>() {
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, PlayerEntity>
    ): MediatorResult {
        val cursor = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKey = localPlayers.getRemoteKey().getOrElse {
                    return MediatorResult.Error(Exception(it.name))
                }
                remoteKey.nextCursor ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }
        val result = remotePlayers.getPlayers(cursor, state.config.pageSize).getOrElse {
            return MediatorResult.Error(Exception(it.name))
        }
        val players = result.asEntityList()
        val meta = result.meta

        if (loadType == LoadType.REFRESH) {
            localPlayers.clearAll()
            localPlayers.clearRemoteKey()
        }

        localPlayers.insertAll(players)
        val previousCursor = meta.previousCursor
        val nextCursor = meta.nextCursor
        localPlayers.insertRemoteKey(previousCursor, nextCursor)

        return MediatorResult.Success(endOfPaginationReached = nextCursor == null)
    }
}