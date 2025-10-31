package com.example.nbaapp.ui.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbaapp.core.helpers.onFailure
import com.example.nbaapp.core.helpers.onSuccess
import com.example.nbaapp.domain.models.Game
import com.example.nbaapp.domain.models.GameListItem
import com.example.nbaapp.domain.repository.Games
import com.example.nbaapp.ui.common.ErrorMessageMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.forEach

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val gamesRepository: Games,
    private val errorMessageMapper: ErrorMessageMapper
) : ViewModel() {
    private val _uiState = MutableLiveData<GamesUiState>()
    val uiState: LiveData<GamesUiState> = _uiState

    fun loadGames(teamId: Int) {
        _uiState.value = GamesUiState.Loading
        viewModelScope.launch {
            gamesRepository.getAll(teamId)
                .onSuccess { games -> _uiState.value = GamesUiState.Success(getGamesList(games)) }
                .onFailure { error -> _uiState.value = GamesUiState.Error(errorMessageMapper.toUiMessage(error)) }
        }
    }

    private fun getGamesList(games: List<Game>): List<GameListItem> {
        return buildList {
            add(GameListItem.Header)
            games.forEach { add(GameListItem.GameRow(it)) }
        }
    }
}