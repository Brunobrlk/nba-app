package com.example.nbaapp.ui.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nbaapp.databinding.ItemLoadingBinding

class LoadingAdapter(
    private val retry: () -> Unit, private val mapError: (Throwable) -> String
) : LoadStateAdapter<LoadingAdapter.LoadingViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, loadState: LoadState
    ): LoadingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLoadingBinding.inflate(inflater, parent, false)
        return LoadingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadingViewHolder, loadState: LoadState) = holder.bind(loadState, retry, mapError)

    class LoadingViewHolder(private val binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState, retry: () -> Unit, mapError: (Throwable) -> String) {
            binding.apply {
                if (loadState is LoadState.Error) {
                    textviewError.text = mapError(loadState.error)
                    buttonRetry.setOnClickListener {
                        retry()
                    }
                }

                loadingIndicator.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState is LoadState.Error
                textviewError.isVisible = loadState is LoadState.Error
            }
        }
    }
}
