package com.example.nbaapp.data.remote

import com.example.nbaapp.data.remote.dtos.ApiDataDto
import com.example.nbaapp.data.remote.dtos.GameDto
import com.example.nbaapp.core.helpers.DataError
import com.example.nbaapp.core.helpers.Result

interface RemoteGamesDataSource {
    suspend fun getAll(teamId: Int): Result<ApiDataDto<List<GameDto>>, DataError.Remote>
}
