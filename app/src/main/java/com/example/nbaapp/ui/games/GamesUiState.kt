package com.example.nbaapp.ui.games

import com.example.nbaapp.domain.models.Game

sealed class GamesUiState {
    data class Success(val games: List<Game>) : GamesUiState()
    data class Error(val message: String) : GamesUiState()
    object Loading : GamesUiState()
}