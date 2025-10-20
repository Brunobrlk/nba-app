package com.example.nbaapp.data.remote.services

import com.example.nbaapp.data.remote.dtos.ApiDataDto
import com.example.nbaapp.data.remote.dtos.PlayerDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlayersService {
    @GET("players")
    suspend fun getPlayers(
        @Query("cursor") cursor: Int,
        @Query("per_page") perPage: Int
    ): Response<ApiDataDto<List<PlayerDto>>>

    @GET("players")
    suspend fun searchPlayers(@Query("search") name: String): Response<ApiDataDto<List<PlayerDto>>>
}