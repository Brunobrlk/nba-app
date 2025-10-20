package com.example.nbaapp.ui.players

import androidx.paging.PagingData
import com.example.nbaapp.domain.models.PlayerListItem


sealed class PlayersUiState {
    object Loading : PlayersUiState()
    data class Success(val players: PagingData<PlayerListItem>) : PlayersUiState()
    data class Warning(val cachedData: PagingData<PlayerListItem>, val message: String) : PlayersUiState()
    data class Error(val message: String) : PlayersUiState()
}