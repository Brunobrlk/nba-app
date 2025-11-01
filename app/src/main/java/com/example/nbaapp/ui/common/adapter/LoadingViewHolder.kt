package com.example.nbaapp.ui.common.adapter

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.databinding.ItemLoadingBinding

class LoadingViewHolder(private val binding: ItemLoadingBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(loadState: LoadState, retry: () -> Unit, mapError: (Throwable) -> String) {
        binding.apply {
            if (loadState is LoadState.Error) {
                textviewErrorText.text = mapError(loadState.error)
                buttonLoadingRetry.setOnClickListener {
                    retry()
                }
            }

            loadingIndicator.isVisible = loadState is LoadState.Loading
            buttonLoadingRetry.isVisible = loadState is LoadState.Error
            textviewErrorText.isVisible = loadState is LoadState.Error
        }
    }
}
