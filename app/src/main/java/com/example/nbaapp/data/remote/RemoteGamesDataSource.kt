package com.example.nbaapp.data.remote

import com.example.nbaapp.data.remote.dtos.GameDto
import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.helpers.Result

interface RemoteGamesDataSource {
    suspend fun getGames(teamId: Int): Result<List<GameDto>, DataError.Remote>
}
