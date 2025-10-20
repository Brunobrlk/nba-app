package com.example.nbaapp.domain.repository

import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.helpers.Result
import com.example.nbaapp.domain.models.Game

interface Games {
    suspend fun getGames(teamId: Int): Result<List<Game>, DataError.Remote>
}