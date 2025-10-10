package com.example.nbaapp.ui.players

import com.example.nbaapp.domain.models.Player


sealed class PlayersUiState {
    object Loading : PlayersUiState()
    data class Success(val players: List<Player>) : PlayersUiState()
    data class Warning(val cachedData: List<Player>, val message: String) : PlayersUiState()
    data class Error(val message: String) : PlayersUiState()
}