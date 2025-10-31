package com.example.nbaapp.domain.models

sealed class TeamListItem {
    data class TeamRow(val team: Team) : TeamListItem()
    object Header: TeamListItem()
}
