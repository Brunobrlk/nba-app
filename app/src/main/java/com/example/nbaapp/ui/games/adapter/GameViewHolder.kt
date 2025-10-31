package com.example.nbaapp.ui.games.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.databinding.ItemGameBinding
import com.example.nbaapp.domain.models.Game
import com.example.nbaapp.ui.common.setTextOrDash

class GameViewHolder(private val binding: ItemGameBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(game: Game) {
        binding.apply {
            textviewHomeTeamName.setTextOrDash(game.homeName)
            textviewHomeTeamScore.setTextOrDash(game.homeScore.toString())
            textviewVisitorTeamName.setTextOrDash(game.visitorName)
            textviewVisitorTeamScore.setTextOrDash(game.visitorScore.toString())
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
