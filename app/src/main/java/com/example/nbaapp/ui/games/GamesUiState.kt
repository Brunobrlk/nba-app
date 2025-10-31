package com.example.nbaapp.ui.games

import com.example.nbaapp.domain.models.GameListItem

sealed class GamesUiState {
    data class Success(val games: List<GameListItem>) : GamesUiState()
    data class Error(val message: String) : GamesUiState()
    object Loading : GamesUiState()
}