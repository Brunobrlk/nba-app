package com.example.nbaapp.data.remote

import com.example.nbaapp.data.remote.dtos.ApiDataDto
import com.example.nbaapp.data.remote.dtos.TeamDto
import com.example.nbaapp.core.utils.DataError
import com.example.nbaapp.core.utils.Result

interface RemoteTeamsDataSource {
    suspend fun getAll(): Result<ApiDataDto<List<TeamDto>>, DataError.Remote>
}
