package com.example.nbaapp.domain.repository

import androidx.paging.PagingData
import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.helpers.Result
import com.example.nbaapp.domain.models.Player
import kotlinx.coroutines.flow.Flow

interface Players {
    suspend fun searchPlayers(name: String): Result<List<Player>, DataError.Remote>
    fun getPlayersPaging(): Flow<PagingData<Player>>
}