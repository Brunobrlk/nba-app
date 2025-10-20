package com.example.nbaapp.domain.models

sealed class GameListItem {
    data class Header(val title: String) : GameListItem()
    data class GameRow(val game: Game) : GameListItem()
}
