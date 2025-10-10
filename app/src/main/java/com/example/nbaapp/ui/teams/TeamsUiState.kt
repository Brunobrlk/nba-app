package com.example.nbaapp.ui.teams

import com.example.nbaapp.domain.models.Team

sealed class TeamsUiState {
    object Loading : TeamsUiState()
    data class Success(val teams: List<Team>) : TeamsUiState()
    data class Warning(val data: List<Team>, val message: String) : TeamsUiState()
    data class Error(val message: String) : TeamsUiState()
}