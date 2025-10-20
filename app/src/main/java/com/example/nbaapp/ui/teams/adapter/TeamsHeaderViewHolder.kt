package com.example.nbaapp.ui.teams.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.databinding.ItemTeamHeaderBinding

class TeamsHeaderViewHolder(binding: ItemTeamHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): TeamsHeaderViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemTeamHeaderBinding.inflate(inflater, parent, false)
            return TeamsHeaderViewHolder(binding)
        }
    }
}
