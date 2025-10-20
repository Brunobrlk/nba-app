package com.example.nbaapp.ui.common.adapter

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.databinding.ItemLoadStateBinding

class LoadingViewHolder(private val binding: ItemLoadStateBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(loadState: LoadState, retry: () -> Unit) {
        binding.apply {
            if (loadState is LoadState.Error) {
                itLoadStateErrorText.text = loadState.error.localizedMessage
                itLoadStateErrorRetryButton.setOnClickListener {
                    retry()
                }
            }

            itLoadStateProgress.isVisible = loadState is LoadState.Loading
            itLoadStateErrorRetryButton.isVisible = loadState is LoadState.Error
            itLoadStateErrorText.isVisible = loadState is LoadState.Error
        }
    }
}
