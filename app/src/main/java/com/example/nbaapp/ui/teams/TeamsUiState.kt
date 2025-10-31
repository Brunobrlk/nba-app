package com.example.nbaapp.ui.teams

import com.example.nbaapp.domain.models.TeamListItem

sealed class TeamsUiState {
    object Loading : TeamsUiState()
    data class Success(val teams: List<TeamListItem>) : TeamsUiState()
    data class Warning(val cache: List<TeamListItem>, val message: String) : TeamsUiState()
    data class Error(val message: String) : TeamsUiState()
}