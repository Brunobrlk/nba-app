package com.example.nbaapp.data.remote

import com.example.nbaapp.data.remote.dtos.ApiDataDto
import com.example.nbaapp.data.remote.dtos.GameDto
import com.example.nbaapp.data.remote.services.GamesService
import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.helpers.Result
import com.example.nbaapp.data.remote.utils.safeCall
import javax.inject.Inject

class RetrofitGamesDataSource @Inject constructor(private val gamesService: GamesService) : RemoteGamesDataSource {
    override suspend fun getGames(teamId: Int): Result<ApiDataDto<List<GameDto>>, DataError.Remote> {
        return safeCall { gamesService.getGames(teamId) }
    }
}
