package com.example.nbaapp.ui.players.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.domain.models.PlayerListItem

class PlayersAdapter(private val onPlayerClickListener: OnPlayerClick) :
    PagingDataAdapter<PlayerListItem, RecyclerView.ViewHolder>(PlayersDiffCallback) {

    companion object {
        private const val TYPE_PLAYER = 0
        private const val TYPE_HEADER = 1
    }

    override fun getItemViewType(position: Int): Int = when (peek(position)) {
        is PlayerListItem.Header -> TYPE_HEADER
        is PlayerListItem.PlayerRow -> TYPE_PLAYER
        null -> TYPE_PLAYER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when {
        viewType == TYPE_HEADER -> PlayersHeaderViewHolder.from(parent)
        else -> PlayerViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is PlayerListItem.PlayerRow -> {
                (holder as PlayerViewHolder).bind(item.player, onPlayerClickListener)
            }

            is PlayerListItem.Header -> {}
            null -> {}
        }
    }
}

interface OnPlayerClick {
    fun onPlayerClick(teamId: Int, teamName: String)
}