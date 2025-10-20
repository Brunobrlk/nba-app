package com.example.nbaapp.data.remote.services

import com.example.nbaapp.data.remote.dtos.ApiDataDto
import com.example.nbaapp.data.remote.dtos.TeamDto
import retrofit2.Response
import retrofit2.http.GET

interface TeamsService {
    @GET("teams")
    suspend fun getTeams(): Response<ApiDataDto<List<TeamDto>>>
}