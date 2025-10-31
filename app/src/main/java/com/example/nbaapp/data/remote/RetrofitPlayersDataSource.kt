package com.example.nbaapp.data.remote

import com.example.nbaapp.data.remote.dtos.ApiDataDto
import com.example.nbaapp.data.remote.dtos.PlayerDto
import com.example.nbaapp.data.remote.services.PlayersService
import com.example.nbaapp.core.helpers.DataError
import javax.inject.Inject
import com.example.nbaapp.core.helpers.Result
import com.example.nbaapp.data.remote.utils.safeCall
import com.example.nbaapp.core.helpers.DebugUtils

class RetrofitPlayersDataSource @Inject constructor(private val playersService: PlayersService) : RemotePlayersDataSource {
    override suspend fun getAll(
        cursor: Int,
        perPage: Int
    ): Result<ApiDataDto<List<PlayerDto>>, DataError.Remote> {
        return safeCall { playersService.getAll(cursor, perPage) }
    }

    override suspend fun search(name: String): Result<ApiDataDto<List<PlayerDto>>, DataError.Remote> {
        return safeCall { playersService.search(name) }
    }
}