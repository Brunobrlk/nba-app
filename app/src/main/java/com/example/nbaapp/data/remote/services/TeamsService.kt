package com.example.nbaapp.data.remote.services

import com.example.nbaapp.data.remote.dtos.ApiDataDto
import com.example.nbaapp.data.remote.dtos.GameDto
import com.example.nbaapp.data.remote.dtos.TeamDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TeamsService {
    @GET("teams")
    suspend fun getTeams(): Response<ApiDataDto<List<TeamDto>>>
}