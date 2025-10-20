package com.example.nbaapp.ui.players.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.databinding.ItemPlayerHeaderBinding

class PlayersHeaderViewHolder(binding: ItemPlayerHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): PlayersHeaderViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemPlayerHeaderBinding.inflate(inflater, parent, false)
            return PlayersHeaderViewHolder(binding)
        }
    }
}
