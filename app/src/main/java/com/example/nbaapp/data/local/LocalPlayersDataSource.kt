package com.example.nbaapp.data.local

import com.example.nbaapp.data.local.database.entities.PlayerEntity
import com.example.nbaapp.domain.helpers.DataError
import com.example.nbaapp.domain.helpers.Result

interface LocalPlayersDataSource {
    fun getPlayers(): Result<List<PlayerEntity>, DataError.Local>
    fun insertPlayers(players: List<PlayerEntity>): Result<Unit, DataError.Local>
}