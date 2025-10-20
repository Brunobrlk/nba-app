package com.example.nbaapp.data.local

import com.example.nbaapp.data.local.database.daos.PlayersDao
import com.example.nbaapp.data.local.database.daos.RemoteKeysDao
import com.example.nbaapp.data.local.database.entities.PlayerEntity
import com.example.nbaapp.data.local.database.entities.RemoteKeys
import com.example.nbaapp.domain.helpers.Constants.REMOTE_KEYS_PLAYERS
import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.helpers.Result
import javax.inject.Inject

class RoomPlayersDataSource @Inject constructor(
    private val playersDao: PlayersDao, private val remoteKeysDao: RemoteKeysDao
) : LocalPlayersDataSource {
    override fun playersPagingSource() = playersDao.pagingSource()

    override suspend fun insertAll(players: List<PlayerEntity>): Result<Unit, DataError.Local> {
        return try {
            playersDao.insertPlayers(players)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun clearAll(): Result<Unit, DataError.Local> {
        return try {
            playersDao.clearAll()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun getRemoteKey(): Result<RemoteKeys, DataError.Local> {
        return try {
            val remoteKey = remoteKeysDao.getRemoteKey(REMOTE_KEYS_PLAYERS)
            Result.Success(remoteKey)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun insertRemoteKey(
        previousCursor: Int?, nextCursor: Int?
    ): Result<Unit, DataError.Local> {
        return try {
            val remoteKey = RemoteKeys(REMOTE_KEYS_PLAYERS, previousCursor, nextCursor)
            remoteKeysDao.insertKey(remoteKey)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun clearRemoteKey(): Result<Unit, DataError.Local> {
        return try {
            remoteKeysDao.clearRemoteKey(REMOTE_KEYS_PLAYERS)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}