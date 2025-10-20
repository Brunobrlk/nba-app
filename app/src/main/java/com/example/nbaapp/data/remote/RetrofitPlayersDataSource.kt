package com.example.nbaapp.data.remote

import com.example.nbaapp.data.remote.dtos.ApiDataDto
import com.example.nbaapp.data.remote.dtos.PlayerDto
import com.example.nbaapp.data.remote.services.PlayersService
import com.example.nbaapp.domain.helpers.DataError
import javax.inject.Inject
import com.example.nbaapp.domain.helpers.Result
import com.example.nbaapp.data.remote.utils.safeCall
import com.example.nbaapp.domain.helpers.DebugUtils

class RetrofitPlayersDataSource @Inject constructor(private val playersService: PlayersService) : RemotePlayersDataSource {
    override suspend fun getPlayers(
        cursor: Int,
        perPage: Int
    ): Result<ApiDataDto<List<PlayerDto>>, DataError.Remote> {
        DebugUtils.fakeLag(5000L)
        return safeCall { playersService.getPlayers(cursor, perPage) }
    }

    override suspend fun searchPlayers(name: String): Result<ApiDataDto<List<PlayerDto>>, DataError.Remote> {
        return safeCall { playersService.searchPlayers(name) }
    }
}