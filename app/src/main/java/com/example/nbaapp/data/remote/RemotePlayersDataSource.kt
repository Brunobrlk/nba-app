package com.example.nbaapp.data.remote

import com.example.nbaapp.data.remote.dtos.ApiDataDto
import com.example.nbaapp.data.remote.dtos.PlayerDto
import com.example.nbaapp.core.helpers.DataError
import com.example.nbaapp.core.helpers.Result

interface RemotePlayersDataSource {
    suspend fun getAll(cursor: Int, perPage: Int): Result<ApiDataDto<List<PlayerDto>>, DataError.Remote>
    suspend fun search(name: String): Result<ApiDataDto<List<PlayerDto>>, DataError.Remote>

}