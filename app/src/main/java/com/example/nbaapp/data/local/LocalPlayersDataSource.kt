package com.example.nbaapp.data.local

import androidx.paging.PagingSource
import com.example.nbaapp.data.local.database.entities.PlayerEntity

interface LocalPlayersDataSource {
    fun playersPagingSource(): PagingSource<Int, PlayerEntity>

    suspend fun insertAll(players: List<PlayerEntity>)

    suspend fun clear()
}