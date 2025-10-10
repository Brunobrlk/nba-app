package com.example.nbaapp.data.local

import com.example.nbaapp.data.local.database.daos.PlayersDao
import com.example.nbaapp.data.local.database.entities.PlayerEntity
import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.helpers.Result
import javax.inject.Inject

class RoomPlayersDataSource @Inject constructor(private val playersDao: PlayersDao) : LocalPlayersDataSource {
    override fun getPlayers(): Result<List<PlayerEntity>, DataError.Local> {
        return try {
            val players = playersDao.getPlayers()
            Result.Success(players)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override fun insertPlayers(players: List<PlayerEntity>): Result<Unit, DataError.Local> {
        return try {
            playersDao.insertPlayers(players)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}