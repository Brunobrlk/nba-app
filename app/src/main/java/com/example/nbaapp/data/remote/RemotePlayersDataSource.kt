package com.example.nbaapp.data.remote

import com.example.nbaapp.data.remote.dtos.ApiDataDto
import com.example.nbaapp.data.remote.dtos.PlayerDto
import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.helpers.Result

interface RemotePlayersDataSource {
    suspend fun getPlayers(cursor: Int, perPage: Int): Result<ApiDataDto<List<PlayerDto>>, DataError.Remote>
    suspend fun searchPlayers(name: String): Result<ApiDataDto<List<PlayerDto>>, DataError.Remote>

}