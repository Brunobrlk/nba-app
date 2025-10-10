package com.example.nbaapp.data.remote.services

import com.example.nbaapp.data.remote.dtos.ApiDataDto
import com.example.nbaapp.data.remote.dtos.GameDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GamesService {
    @GET("games")
    suspend fun getGames(@Query("team_ids[]") teamId: Int): Response<ApiDataDto<List<GameDto>>>
}