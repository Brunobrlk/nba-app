package com.example.nbaapp.ui.games.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.domain.models.GameListItem

class GamesAdapter() : ListAdapter<GameListItem, RecyclerView.ViewHolder>(GamesDiffCallback) {
    companion object {
        private const val TYPE_GAME = 0
        private const val TYPE_HEADER = 1
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is GameListItem.Header -> TYPE_HEADER
        is GameListItem.GameRow -> TYPE_GAME
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_HEADER -> GamesHeaderViewHolder.from(parent)
        else -> GameViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is GameListItem.GameRow -> {
                (holder as GameViewHolder).bind(item.game)
            }

            is GameListItem.Header -> {}
        }
    }

}