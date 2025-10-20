package com.example.nbaapp.data.remote

import com.example.nbaapp.data.remote.dtos.ApiDataDto
import com.example.nbaapp.data.remote.dtos.TeamDto
import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.helpers.Result

interface RemoteTeamsDataSource {
    suspend fun getTeams(): Result<ApiDataDto<List<TeamDto>>, DataError.Remote>
}
