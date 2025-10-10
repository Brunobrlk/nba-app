package com.example.nbaapp.ui.players

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbaapp.domain.repository.Players
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.nbaapp.domain.helpers.Result

@HiltViewModel
class PlayersViewModel @Inject constructor(private val playersRepository: Players) : ViewModel() {
    private val _uiState = MutableLiveData<PlayersUiState>()
    val uiState: LiveData<PlayersUiState> = _uiState

    init {
        getPlayers()
    }

    fun searchPlayers(name: String){
        viewModelScope.launch {
            _uiState.value = PlayersUiState.Loading
            val playersResult = playersRepository.searchPlayers(name)
            when(playersResult) {
                is Result.Success -> _uiState.value = PlayersUiState.Success(playersResult.data)
                is Result.Error -> _uiState.value = PlayersUiState.Error(playersResult.error.toString())
            }
        }
    }

    fun getPlayers(){
        viewModelScope.launch {
            _uiState.value = PlayersUiState.Loading
            val playersResult = playersRepository.getPlayers()
            when(playersResult) {
                is Result.Success -> {
                    _uiState.value = PlayersUiState.Success(playersResult.data)
                }
                is Result.Error -> {
                    if(playersResult.cachedData != null){
                        _uiState.value = PlayersUiState.Warning(playersResult.cachedData, playersResult.error.toString())
                    } else {
                        _uiState.value = PlayersUiState.Error(playersResult.error.toString())
                    }
                }
            }
        }
    }
}