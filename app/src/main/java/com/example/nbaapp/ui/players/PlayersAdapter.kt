package com.example.nbaapp.ui.players

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.databinding.ItemPlayerBinding
import com.example.nbaapp.databinding.ItemPlayerHeaderBinding
import com.example.nbaapp.domain.models.Player
import com.example.nbaapp.domain.models.Team

sealed class PlayerListItem {
    data class PlayerRow(val player: Player) : PlayerListItem()
    data class Header(val title: String) : PlayerListItem()
}

class PlayersAdapter(private val onPlayerClickListener: OnPlayerClick) : ListAdapter<PlayerListItem, RecyclerView.ViewHolder>(
    PlayerDiffCallback
) {

    companion object {
        private const val TYPE_PLAYER = 0
        private const val TYPE_HEADER = 1
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is PlayerListItem.Header -> TYPE_HEADER
        is PlayerListItem.PlayerRow -> TYPE_PLAYER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when {
        viewType == TYPE_HEADER -> HeaderViewHolder.from(parent)
        else -> PlayerViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is PlayerListItem.PlayerRow -> { (holder as PlayerViewHolder).bind(item.player, onPlayerClickListener) }
            is PlayerListItem.Header -> {}
        }
    }

    class HeaderViewHolder(binding: ItemPlayerHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemPlayerHeaderBinding.inflate(inflater, parent, false)
                return HeaderViewHolder(binding)
            }
        }
    }

    class PlayerViewHolder(private val binding: ItemPlayerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Player, onTeamClick: OnPlayerClick) {
            binding.apply {
                textviewCol1Item.text = item.firstName
                textviewCol2Item.text = item.lastName
                textviewCol3Item.text = item.teamName
                buttonOpen.setOnClickListener {
                    onTeamClick.onPlayerClick(item.teamId, item.teamName)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): PlayerViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemPlayerBinding.inflate(inflater, parent, false)
                return PlayerViewHolder(binding)
            }
        }
    }
}

object PlayerDiffCallback : DiffUtil.ItemCallback<PlayerListItem>() {
    override fun areItemsTheSame(
        oldItem: PlayerListItem,
        newItem: PlayerListItem
    ): Boolean {
        return when {
            oldItem is PlayerListItem.Header && newItem is PlayerListItem.Header ->
                oldItem.title == newItem.title
            oldItem is PlayerListItem.PlayerRow && newItem is PlayerListItem.PlayerRow ->
                oldItem.player.firstName == newItem.player.firstName
            else -> false
        }
    }

    override fun areContentsTheSame(
        oldItem: PlayerListItem,
        newItem: PlayerListItem
    ): Boolean {
        return oldItem == newItem
    }

}
interface OnPlayerClick {
    fun onPlayerClick(teamId: Int, teamName: String)
}
