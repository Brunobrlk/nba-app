package com.example.nbaapp.ui.teams

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.databinding.ItemTeamBinding
import com.example.nbaapp.databinding.ItemTeamHeaderBinding
import com.example.nbaapp.domain.models.Team

sealed class TeamListItem {
    data class Header(val title: String) : TeamListItem()
    data class TeamRow(val team: Team) : TeamListItem()
}

class TeamsAdapter(private val onTeamClickListener: OnTeamClick) :
    ListAdapter<TeamListItem, RecyclerView.ViewHolder>(TeamDiffCallback) {
    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_TEAM = 1
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is TeamListItem.Header -> TYPE_HEADER
        is TeamListItem.TeamRow -> TYPE_TEAM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_HEADER -> HeaderViewHolder.from(parent)
        else -> TeamViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is TeamListItem.TeamRow -> {
                (holder as TeamViewHolder).bind(item.team, onTeamClickListener)
            }

            is TeamListItem.Header -> {}
        }
    }

    class HeaderViewHolder(binding: ItemTeamHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemTeamHeaderBinding.inflate(inflater, parent, false)
                return HeaderViewHolder(binding)
            }
        }
    }

    class TeamViewHolder(private val binding: ItemTeamBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Team, onTeamClick: OnTeamClick) {
            binding.apply {
                textviewCol1Item.text = item.name
                textviewCol2Item.text = item.city
                textviewCol3Item.text = item.conference
                buttonOpen.setOnClickListener {
                    onTeamClick.onTeamClick(item)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): TeamViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemTeamBinding.inflate(inflater, parent, false)
                return TeamViewHolder(binding)
            }
        }
    }
}

object TeamDiffCallback : DiffUtil.ItemCallback<TeamListItem>() {
    override fun areItemsTheSame(
        oldItem: TeamListItem,
        newItem: TeamListItem
    ): Boolean {
        return when {
            oldItem is TeamListItem.Header && newItem is TeamListItem.Header ->
                oldItem.title == newItem.title

            oldItem is TeamListItem.TeamRow && newItem is TeamListItem.TeamRow ->
                oldItem.team.name == newItem.team.name

            else -> false
        }
    }

    override fun areContentsTheSame(
        oldItem: TeamListItem,
        newItem: TeamListItem
    ): Boolean {
        return oldItem == newItem
    }
}

interface OnTeamClick {
    fun onTeamClick(team: Team)
}
