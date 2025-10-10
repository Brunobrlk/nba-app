package com.example.nbaapp.data.remote

import com.example.nbaapp.data.remote.dtos.GameDto
import com.example.nbaapp.data.remote.services.GamesService
import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.helpers.Result
import javax.inject.Inject

class RetrofitGamesDataSource @Inject constructor(private val gamesService: GamesService): RemoteGamesDataSource {
    override suspend fun getGames(teamId: Int): Result<List<GameDto>, DataError.Remote> {
        return try {
            val response = gamesService.getGames(teamId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!.data)
            } else {
                when(response.code()) {
                    401 -> Result.Error(DataError.Remote.UNAUTHORIZED)
                    400 -> Result.Error(DataError.Remote.BAD_REQUEST)
                    404	-> Result.Error(DataError.Remote.NOT_FOUND)
                    406	-> Result.Error(DataError.Remote.NOT_ACCEPTABLE)
                    429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
                    500	-> Result.Error(DataError.Remote.INTERNAL_SERVER_ERROR)
                    503 -> Result.Error(DataError.Remote.SERVICE_UNAVAILABLE)
                    else -> Result.Error(DataError.Remote.UNKNOWN)
                }
            }
        } catch (e: Exception) {
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }
}
