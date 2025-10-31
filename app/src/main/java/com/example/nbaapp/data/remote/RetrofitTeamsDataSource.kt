package com.example.nbaapp.data.remote

import com.example.nbaapp.data.remote.dtos.ApiDataDto
import com.example.nbaapp.data.remote.dtos.TeamDto
import com.example.nbaapp.data.remote.services.TeamsService
import com.example.nbaapp.core.helpers.DataError
import com.example.nbaapp.core.helpers.Result
import com.example.nbaapp.data.remote.utils.safeCall
import javax.inject.Inject

class RetrofitTeamsDataSource @Inject constructor(private val teamsService: TeamsService) : RemoteTeamsDataSource {
    override suspend fun getAll(): Result<ApiDataDto<List<TeamDto>>, DataError.Remote> {
        return safeCall { teamsService.getAll() }
    }
}