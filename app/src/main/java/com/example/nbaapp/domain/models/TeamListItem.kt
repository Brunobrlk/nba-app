package com.example.nbaapp.domain.models

sealed class TeamListItem {
    data class Header(val title: String) : TeamListItem()
    data class TeamRow(val team: Team) : TeamListItem()
}
