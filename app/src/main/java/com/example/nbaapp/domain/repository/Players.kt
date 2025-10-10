package com.example.nbaapp.domain.repository

import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.models.Player
import com.example.nbaapp.domain.helpers.Result

interface Players {
    suspend fun getPlayers(): Result<List<Player>, DataError>
    suspend fun searchPlayers(name: String): Result<List<Player>, DataError.Remote>
}