package com.example.nbaapp.data.remote

import com.example.nbaapp.data.remote.dtos.ApiDataDto
import com.example.nbaapp.data.remote.dtos.GameDto
import com.example.nbaapp.core.utils.DataError
import com.example.nbaapp.core.utils.Result

interface RemoteGamesDataSource {
    suspend fun getAll(teamId: Int): Result<ApiDataDto<List<GameDto>>, DataError.Remote>
}
