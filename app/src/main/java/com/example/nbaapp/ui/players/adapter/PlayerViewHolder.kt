package com.example.nbaapp.ui.players.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.databinding.ItemPlayerBinding
import com.example.nbaapp.domain.models.Player
import com.example.nbaapp.ui.common.setTextOrDash

class PlayerViewHolder(private val binding: ItemPlayerBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Player, onTeamClick: OnPlayerClick) {
        binding.apply {
            textviewCol1Item.setTextOrDash(item.firstName)
            textviewCol2Item.setTextOrDash(item.lastName)
            textviewCol3Item.setTextOrDash(item.teamName)
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
