package com.example.nbaapp.ui.teams.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.domain.models.Team
import com.example.nbaapp.domain.models.TeamListItem


class TeamsAdapter(private val onTeamClickListener: OnTeamClick) :
    ListAdapter<TeamListItem, RecyclerView.ViewHolder>(TeamsDiffCallback) {
    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_TEAM = 1
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is TeamListItem.Header -> TYPE_HEADER
        is TeamListItem.TeamRow -> TYPE_TEAM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_HEADER -> TeamsHeaderViewHolder.from(parent)
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
}

interface OnTeamClick {
    fun onTeamClick(team: Team)
}
