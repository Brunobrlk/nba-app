package com.example.nbaapp.ui.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbaapp.domain.repository.Games
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.nbaapp.domain.helpers.Result

@HiltViewModel
class GamesViewModel @Inject constructor(private val gamesRepository: Games) : ViewModel() {
    private val _uiState = MutableLiveData<GamesUiState>()
    val uiState: LiveData<GamesUiState> = _uiState

    fun loadGames(teamId: Int) {
        _uiState.value = GamesUiState.Loading
        viewModelScope.launch {
            val gamesResult = gamesRepository.getGames(teamId)
            when (gamesResult) {
                is Result.Success -> _uiState.value = GamesUiState.Success(gamesResult.data)
                is Result.Error -> _uiState.value = GamesUiState.Error(gamesResult.error.toString())
            }
        }
    }
}