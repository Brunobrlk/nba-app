package com.example.nbaapp.domain.models

sealed class GameListItem {
    data class GameRow(val game: Game) : GameListItem()
    object Header: GameListItem()
}
