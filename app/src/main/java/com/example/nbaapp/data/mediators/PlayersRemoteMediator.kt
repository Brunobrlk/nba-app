package com.example.nbaapp.data.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.nbaapp.data.local.LocalPlayersDataSource
import com.example.nbaapp.data.local.LocalRemoteKeysDataSource
import com.example.nbaapp.data.local.database.entities.PlayerEntity
import com.example.nbaapp.data.local.database.entities.RemoteKeyEntity
import com.example.nbaapp.data.mappers.asEntityList
import com.example.nbaapp.data.remote.RemotePlayersDataSource
import com.example.nbaapp.core.utils.Constants
import com.example.nbaapp.core.utils.getOrElse
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PlayersRemoteMediator @Inject constructor(
    private val remotePlayers: RemotePlayersDataSource,
    private val localPlayers: LocalPlayersDataSource,
    private val localKeys: LocalRemoteKeysDataSource
) : RemoteMediator<Int, PlayerEntity>() {
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, PlayerEntity>
    ): MediatorResult {
        val cursor = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKey = localKeys.get(Constants.REMOTE_KEYS_PLAYERS)
                remoteKey.nextCursor ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }
        val result = remotePlayers.getAll(cursor, state.config.pageSize).getOrElse { error ->
            return MediatorResult.Error(DataErrorException(error))
        }

        if (loadType == LoadType.REFRESH) {
            localPlayers.clear()
            localKeys.delete(Constants.REMOTE_KEYS_PLAYERS)
        }

        val players = result.asEntityList()
        localPlayers.insertAll(players)

        val meta = result.meta
        val previousCursor = meta.previousCursor
        val nextCursor = meta.nextCursor
        val remoteKey = RemoteKeyEntity(Constants.REMOTE_KEYS_PLAYERS, previousCursor, nextCursor)
        localKeys.insert(remoteKey)

        return MediatorResult.Success(endOfPaginationReached = nextCursor == null)
    }
}