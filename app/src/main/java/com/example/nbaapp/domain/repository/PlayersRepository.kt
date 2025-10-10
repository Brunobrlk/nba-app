package com.example.nbaapp.domain.repository

import com.example.nbaapp.data.local.LocalPlayersDataSource
import com.example.nbaapp.data.local.database.entities.asDomainList
import com.example.nbaapp.data.remote.RemotePlayersDataSource
import com.example.nbaapp.data.remote.dtos.asDomainList
import com.example.nbaapp.data.remote.dtos.asEntityList
import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.models.Player
import com.example.nbaapp.domain.helpers.Result
import com.example.nbaapp.domain.helpers.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlayersRepository @Inject constructor(
    private val remotePlayers: RemotePlayersDataSource,
    private val localPlayers: LocalPlayersDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : Players {
    override suspend fun getPlayers(): Result<List<Player>, DataError> {
        return withContext(ioDispatcher) {
            val playersDto = remotePlayers.getPlayers()
            when(playersDto){
                is Result.Success -> {
                    localPlayers.insertPlayers(playersDto.data.asEntityList() )
                    localPlayers.getPlayers().map { it.asDomainList() }
                }
                is Result.Error -> {
                    val cachedPlayers = localPlayers.getPlayers()
                    if (cachedPlayers is Result.Success) {
                        Result.Error(
                            playersDto.error,
                            cachedPlayers.data.asDomainList()
                        )
                    } else {
                        Result.Error(playersDto.error)
                    }
                }
            }
        }
    }

    override suspend fun searchPlayers(name: String): Result<List<Player>, DataError.Remote> {
        return withContext(ioDispatcher) {
            remotePlayers.searchPlayers(name).map { it.asDomainList() }
        }
    }
}