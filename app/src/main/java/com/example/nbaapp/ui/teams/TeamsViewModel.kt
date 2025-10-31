package com.example.nbaapp.ui.teams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbaapp.core.helpers.onFailureWithCache
import com.example.nbaapp.core.helpers.onFailure
import com.example.nbaapp.core.helpers.onSuccess
import com.example.nbaapp.data.local.database.utils.SortTeamBy
import com.example.nbaapp.domain.models.Team
import com.example.nbaapp.domain.models.TeamListItem
import com.example.nbaapp.domain.repository.Teams
import com.example.nbaapp.ui.common.ErrorMessageMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.forEach

@HiltViewModel
class TeamsViewModel @Inject constructor(
    private val teamsRepository: Teams, private val errorMessageMapper: ErrorMessageMapper
) : ViewModel() {

    private val _uiState = MutableLiveData<TeamsUiState>()
    val uiState: LiveData<TeamsUiState> = _uiState

    private val _currentSort = MutableLiveData(SortTeamBy.NAME)
    val currentSort: LiveData<SortTeamBy> = _currentSort


    init {
        getTeams()
    }

    fun getTeamsOrdered(sort: SortTeamBy, isAscending: Boolean) {
        viewModelScope.launch {
            teamsRepository.getAllOrdered(sort, isAscending).onSuccess { teams ->
                _uiState.value = TeamsUiState.Success(getTeamsListItem(teams))
            }.onFailure {
                _uiState.value = TeamsUiState.Error(errorMessageMapper.toUiMessage(it))
            }
        }
    }

    fun getTeams() {
        _uiState.value = TeamsUiState.Loading
        viewModelScope.launch {
            teamsRepository.getAll().onSuccess { teams ->
                _uiState.value = TeamsUiState.Success(getTeamsListItem(teams))
            }.onFailureWithCache { error, cachedTeams ->
                val uiError = errorMessageMapper.toUiMessage(error)
                _uiState.value = if (cachedTeams.isNullOrEmpty()) TeamsUiState.Error(uiError)
                else TeamsUiState.Warning(getTeamsListItem(cachedTeams), uiError)
            }
        }
    }
    private fun getTeamsListItem(players: List<Team>): List<TeamListItem> {
        return buildList {
            add(TeamListItem.Header)
            players.forEach { add(TeamListItem.TeamRow(it)) }
        }
    }
}