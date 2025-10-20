package com.example.nbaapp.ui.games.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.databinding.ItemGameHeaderBinding

class GamesHeaderViewHolder(binding: ItemGameHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): GamesHeaderViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemGameHeaderBinding.inflate(inflater, parent, false)
            return GamesHeaderViewHolder(binding)
        }
    }
}
