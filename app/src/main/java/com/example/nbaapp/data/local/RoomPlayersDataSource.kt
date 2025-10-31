package com.example.nbaapp.data.local

import com.example.nbaapp.data.local.database.daos.PlayersDao
import com.example.nbaapp.data.local.database.daos.RemoteKeysDao
import com.example.nbaapp.data.local.database.entities.PlayerEntity
import javax.inject.Inject

class RoomPlayersDataSource @Inject constructor(
    private val playersDao: PlayersDao
) : LocalPlayersDataSource {
    override fun playersPagingSource() = playersDao.pagingSource()
    override suspend fun insertAll(players: List<PlayerEntity>) = playersDao.insertAll(players)
    override suspend fun clear() = playersDao.clear()
}