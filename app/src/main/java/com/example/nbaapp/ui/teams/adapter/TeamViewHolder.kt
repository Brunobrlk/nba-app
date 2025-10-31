package com.example.nbaapp.ui.teams.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.databinding.ItemTeamBinding
import com.example.nbaapp.domain.models.Team
import com.example.nbaapp.ui.common.setTextOrDash

class TeamViewHolder(private val binding: ItemTeamBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Team, onTeamClick: OnTeamClick) {
        binding.apply {
            textviewCol1Item.setTextOrDash(item.name)
            textviewCol2Item.setTextOrDash(item.city)
            textviewCol3Item.setTextOrDash(item.conference)
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
