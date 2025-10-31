package com.example.nbaapp.domain.repository

import com.example.nbaapp.core.helpers.DataError
import com.example.nbaapp.core.helpers.Result
import com.example.nbaapp.domain.models.Game

interface Games {
    suspend fun getAll(teamId: Int): Result<List<Game>, DataError.Remote>
}