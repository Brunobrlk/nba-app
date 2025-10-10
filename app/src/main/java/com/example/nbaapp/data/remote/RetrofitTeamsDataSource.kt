package com.example.nbaapp.data.remote

import com.example.nbaapp.data.remote.dtos.TeamDto
import com.example.nbaapp.data.remote.services.TeamsService
import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.helpers.Result
import javax.inject.Inject

class RetrofitTeamsDataSource @Inject constructor(private val teamsService: TeamsService) : RemoteTeamsDataSource {
    override suspend fun getTeams(): Result<List<TeamDto>, DataError.Remote> {
        return try {
            val response = teamsService.getTeams()
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