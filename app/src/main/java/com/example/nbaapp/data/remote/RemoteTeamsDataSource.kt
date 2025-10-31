package com.example.nbaapp.data.remote

import com.example.nbaapp.data.remote.dtos.ApiDataDto
import com.example.nbaapp.data.remote.dtos.TeamDto
import com.example.nbaapp.core.helpers.DataError
import com.example.nbaapp.core.helpers.Result

interface RemoteTeamsDataSource {
    suspend fun getAll(): Result<ApiDataDto<List<TeamDto>>, DataError.Remote>
}
