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
import com.example.nbaapp.core.helpers.DataError
import com.example.nbaapp.core.helpers.onFailure
import com.example.nbaapp.core.helpers.onSuccess
import com.example.nbaapp.data.mediators.DataErrorException
import com.example.nbaapp.domain.models.Player
import com.example.nbaapp.domain.models.PlayerListItem
import com.example.nbaapp.domain.repository.Players
import com.example.nbaapp.ui.common.ErrorMessageMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    private val playersRepository: Players, private val errorMessageMapper: ErrorMessageMapper
) : ViewModel() {
    private val _uiState = MutableLiveData<PlayersUiState>()
    val uiState: LiveData<PlayersUiState> = _uiState

    val players = playersRepository.getPlayersPaging()
        .map { paging -> paging.map<Player, PlayerListItem> { toListItem(it) } }
        .map { it.insertHeaderItem(TerminalSeparatorType.SOURCE_COMPLETE, PlayerListItem.Header) }
        .cachedIn(viewModelScope)
        .asLiveData(viewModelScope.coroutineContext)

    private fun toListItem(player: Player) = PlayerListItem.PlayerRow(player)

    fun searchPlayers(name: String) {
        _uiState.value = PlayersUiState.Loading
        viewModelScope.launch {
            playersRepository.search(name).onSuccess { players ->
                val playersList = getPlayersList(players)
                val pagingData = PagingData.from(playersList)
                _uiState.value = PlayersUiState.Success(pagingData)
            }.onFailure { error ->
                _uiState.value = PlayersUiState.Error(errorMessageMapper.toUiMessage(error))
            }
        }
    }

    fun mapPagingError(error: Throwable) = if (error is DataErrorException) {
        errorMessageMapper.toUiMessage(error.dataError)
    } else {
        errorMessageMapper.toUiMessage(DataError.Remote.UNKNOWN)
    }

    private fun getPlayersList(players: List<Player>): List<PlayerListItem> {
        return buildList {
            add(PlayerListItem.Header)
            players.forEach { add(PlayerListItem.PlayerRow(it)) }
        }
    }
}
