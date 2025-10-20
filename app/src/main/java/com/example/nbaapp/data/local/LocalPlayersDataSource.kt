package com.example.nbaapp.data.local

import androidx.paging.PagingSource
import com.example.nbaapp.data.local.database.entities.PlayerEntity
import com.example.nbaapp.data.local.database.entities.RemoteKeys
import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.helpers.Result

interface LocalPlayersDataSource {
    fun playersPagingSource(): PagingSource<Int, PlayerEntity>

    suspend fun insertAll(players: List<PlayerEntity>): Result<Unit, DataError.Local>

    suspend fun clearAll(): Result<Unit, DataError.Local>

    suspend fun getRemoteKey(): Result<RemoteKeys, DataError.Local>

    suspend fun insertRemoteKey(previousCursor: Int?, nextCursor: Int?): Result<Unit, DataError.Local>

    suspend fun clearRemoteKey(): Result<Unit, DataError.Local>
}