package com.example.nbaapp.ui.players.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.nbaapp.domain.models.PlayerListItem

object PlayersDiffCallback : DiffUtil.ItemCallback<PlayerListItem>() {
    override fun areItemsTheSame(
        oldItem: PlayerListItem, newItem: PlayerListItem
    ): Boolean {
        return when {
            oldItem is PlayerListItem.Header && newItem is PlayerListItem.Header -> true
            oldItem is PlayerListItem.PlayerRow && newItem is PlayerListItem.PlayerRow -> oldItem.player.firstName == newItem.player.firstName
            else -> false
        }
    }

    override fun areContentsTheSame(
        oldItem: PlayerListItem, newItem: PlayerListItem
    ): Boolean {
        return oldItem == newItem
    }

}
