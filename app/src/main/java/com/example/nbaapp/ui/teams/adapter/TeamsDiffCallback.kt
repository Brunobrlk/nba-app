package com.example.nbaapp.ui.teams.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.nbaapp.domain.models.TeamListItem

object TeamsDiffCallback : DiffUtil.ItemCallback<TeamListItem>() {
    override fun areItemsTheSame(
        oldItem: TeamListItem, newItem: TeamListItem
    ): Boolean {
        return when {
            oldItem is TeamListItem.Header && newItem is TeamListItem.Header -> oldItem.title == newItem.title
            oldItem is TeamListItem.TeamRow && newItem is TeamListItem.TeamRow -> oldItem.team.name == newItem.team.name
            else -> false
        }
    }

    override fun areContentsTheSame(
        oldItem: TeamListItem, newItem: TeamListItem
    ): Boolean {
        return oldItem == newItem
    }
}
