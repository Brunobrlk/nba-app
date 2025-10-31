package com.example.nbaapp.data.remote

import com.example.nbaapp.data.remote.dtos.ApiDataDto
import com.example.nbaapp.data.remote.dtos.GameDto
import com.example.nbaapp.data.remote.services.GamesService
import com.example.nbaapp.core.helpers.DataError
import com.example.nbaapp.core.helpers.Result
import com.example.nbaapp.data.remote.utils.safeCall
import javax.inject.Inject

class RetrofitGamesDataSource @Inject constructor(private val gamesService: GamesService) : RemoteGamesDataSource {
    override suspend fun getAll(teamId: Int): Result<ApiDataDto<List<GameDto>>, DataError.Remote> {
        return safeCall { gamesService.getAll(teamId) }
    }
}
