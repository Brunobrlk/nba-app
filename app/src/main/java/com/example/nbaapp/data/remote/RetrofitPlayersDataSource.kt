package com.example.nbaapp.data.remote

import com.example.nbaapp.data.remote.dtos.PlayerDto
import com.example.nbaapp.data.remote.services.PlayersService
import com.example.nbaapp.domain.helpers.DataError
import javax.inject.Inject
import com.example.nbaapp.domain.helpers.Result

class RetrofitPlayersDataSource @Inject constructor(private val playersService: PlayersService) : RemotePlayersDataSource {
    override suspend fun getPlayers(): Result<List<PlayerDto>, DataError.Remote> {
        return try {
            val response = playersService.getPlayers()
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
        } catch (e: Exception){
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }

    override suspend fun searchPlayers(name: String): Result<List<PlayerDto>, DataError.Remote> {
        return try {
            val response = playersService.searchPlayers(name)
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
        } catch (e: Exception){
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }
}