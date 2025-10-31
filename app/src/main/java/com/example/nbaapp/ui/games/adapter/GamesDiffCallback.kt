package com.example.nbaapp.ui.games.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.nbaapp.domain.models.GameListItem

object GamesDiffCallback : DiffUtil.ItemCallback<GameListItem>() {
    override fun areItemsTheSame(
        oldItem: GameListItem, newItem: GameListItem
    ) = when {
        oldItem is GameListItem.Header && newItem is GameListItem.Header -> true
        oldItem is GameListItem.GameRow && newItem is GameListItem.GameRow -> oldItem.game.homeName == newItem.game.homeName
        else -> false
    }

    override fun areContentsTheSame(
        oldItem: GameListItem, newItem: GameListItem
    ) = oldItem == newItem
}
