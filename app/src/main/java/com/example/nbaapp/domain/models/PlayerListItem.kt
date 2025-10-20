package com.example.nbaapp.domain.models

sealed class PlayerListItem {
    data class PlayerRow(val player: Player) : PlayerListItem()
    object Header : PlayerListItem()
}
