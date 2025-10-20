package com.example.nbaapp.ui.players

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.TerminalSeparatorType
import androidx.paging.cachedIn
import androidx.paging.insertHeaderItem
import androidx.paging.map
import com.example.nbaapp.domain.helpers.Result
import com.example.nbaapp.domain.models.Player
import com.example.nbaapp.domain.models.PlayerListItem
import com.example.nbaapp.domain.repository.Players
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(private val playersRepository: Players) : ViewModel() {
    private val _uiState = MutableLiveData<PlayersUiState>()
    val uiState: LiveData<PlayersUiState> = _uiState

    val playersLiveData =
        playersRepository.getPlayersPaging()
            .map { pagingData -> pagingData.map<Player, PlayerListItem> { PlayerListItem.PlayerRow(it) } }
            .map { it.insertHeaderItem(TerminalSeparatorType.SOURCE_COMPLETE, PlayerListItem.Header) }
            .cachedIn(viewModelScope)
            .asLiveData(viewModelScope.coroutineContext)

    fun searchPlayers(name: String) {
        viewModelScope.launch {
            _uiState.value = PlayersUiState.Loading
            val playersResult = playersRepository.searchPlayers(name)
            when (playersResult) {
                is Result.Success -> {
                    val playersList = getPlayersList(playersResult.data)
                    val pagingData = PagingData.from(playersList)
                    _uiState.value = PlayersUiState.Success(pagingData)
                }
                is Result.Error -> _uiState.value = PlayersUiState.Error(playersResult.error.toString())
            }
        }
    }

    private fun getPlayersList(players: List<Player>): List<PlayerListItem> {
        return buildList {
            add(PlayerListItem.Header)
            players.forEach { add(PlayerListItem.PlayerRow(it)) }
        }
    }
}
