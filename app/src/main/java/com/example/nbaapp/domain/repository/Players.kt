package com.example.nbaapp.domain.repository

import androidx.paging.PagingData
import com.example.nbaapp.core.helpers.DataError
import com.example.nbaapp.core.helpers.Result
import com.example.nbaapp.domain.models.Player
import kotlinx.coroutines.flow.Flow

interface Players {
    suspend fun search(name: String): Result<List<Player>, DataError.Remote>
    fun getPlayersPaging(): Flow<PagingData<Player>>
}