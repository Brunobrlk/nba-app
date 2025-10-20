package com.example.nbaapp.ui.teams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbaapp.data.local.database.utils.SortTeamBy
import com.example.nbaapp.domain.helpers.Result
import com.example.nbaapp.domain.repository.Teams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor(private val teamsRepository: Teams) : ViewModel() {

    private val _uiState = MutableLiveData<TeamsUiState>()
    val uiState: LiveData<TeamsUiState> = _uiState

    private val _currentSort = MutableLiveData(SortTeamBy.NAME)
    val currentSort: LiveData<SortTeamBy> = _currentSort


    init {
        getTeams()
    }

    fun getTeamsOrdered(sort: SortTeamBy, isAscending: Boolean) {
        viewModelScope.launch {
            val teamsResult = teamsRepository.getTeamsOrdered(sort, isAscending)
            when (teamsResult) {
                is Result.Success -> {
                    _uiState.value = TeamsUiState.Success(teamsResult.data)
                    _currentSort.value = sort
                }

                is Result.Error -> {
                    _uiState.value = TeamsUiState.Error(teamsResult.error.toString())
                }
            }
        }
    }

    fun getTeams() {
        viewModelScope.launch {
            _uiState.value = TeamsUiState.Loading
            val teamsResult = teamsRepository.getTeams()
            when (teamsResult) {
                is Result.Success -> _uiState.value = TeamsUiState.Success(teamsResult.data)
                is Result.Error -> {
                    if (teamsResult.cachedData != null) {
                        _uiState.value = TeamsUiState.Warning(
                            teamsResult.cachedData,
                            teamsResult.error.toString()
                        )
                    } else {
                        _uiState.value = TeamsUiState.Error(teamsResult.error.toString())
                    }
                }
            }
        }
    }
}