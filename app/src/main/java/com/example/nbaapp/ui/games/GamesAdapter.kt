package com.example.nbaapp.ui.games

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.databinding.ItemGameBinding
import com.example.nbaapp.databinding.ItemGameHeaderBinding
import com.example.nbaapp.domain.models.Game

sealed class GameListItem {
    data class Header(val title: String) : GameListItem()
    data class GameRow(val game: Game) : GameListItem()
}

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
        TYPE_HEADER -> HeaderViewHolder.from(parent)
        else -> GameViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is GameListItem.GameRow -> { (holder as GameViewHolder).bind(item.game) }
            is GameListItem.Header -> {}
        }
    }

    class HeaderViewHolder(binding: ItemGameHeaderBinding) : RecyclerView.ViewHolder(binding.root){
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemGameHeaderBinding.inflate(inflater, parent, false)
                return HeaderViewHolder(binding)
            }
        }
    }
    class GameViewHolder(private val binding: ItemGameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(game: Game) {
            binding.apply {
                textviewHomeTeamName.text = game.homeName
                textviewHomeTeamScore.text = game.homeScore.toString()
                textviewVisitorTeamName.text = game.visitorName
                textviewVisitorTeamScore.text = game.visitorScore.toString()
            }
        }

        companion object {
            fun from(parent: ViewGroup): GameViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemGameBinding.inflate(inflater, parent, false)
                return GameViewHolder(binding)
            }
        }
    }
}

object GamesDiffCallback : DiffUtil.ItemCallback<GameListItem>() {
    override fun areItemsTheSame(
        oldItem: GameListItem,
        newItem: GameListItem
    ): Boolean {
        return when {
            oldItem is GameListItem.Header && newItem is GameListItem.Header ->
                oldItem.title == newItem.title
            oldItem is GameListItem.GameRow && newItem is GameListItem.GameRow ->
                oldItem.game.homeName == newItem.game.homeName
            else -> false
        }
    }

    override fun areContentsTheSame(
        oldItem: GameListItem,
        newItem: GameListItem
    ): Boolean {
        return oldItem == newItem
    }
}

