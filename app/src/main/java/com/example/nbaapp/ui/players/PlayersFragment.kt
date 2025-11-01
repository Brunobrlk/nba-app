package com.example.nbaapp.ui.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.transition.TransitionManager
import com.example.nbaapp.R
import com.example.nbaapp.core.helpers.DebugUtils
import com.example.nbaapp.databinding.FragmentPlayersBinding
import com.example.nbaapp.domain.models.PlayerListItem
import com.example.nbaapp.ui.common.adapter.LoadingAdapter
import com.example.nbaapp.ui.players.adapter.OnPlayerClick
import com.example.nbaapp.ui.players.adapter.PlayersAdapter
import com.google.android.material.search.SearchView.TransitionState
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PlayersFragment : Fragment(), OnPlayerClick {
    private lateinit var binding: FragmentPlayersBinding
    private lateinit var playersAdapter: PlayersAdapter
    private val viewModel: PlayersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayersBinding.inflate(inflater, container, false)
        initViews()
        initListeners()
        return binding.root
    }

    override fun onPlayerClick(teamId: Int, teamName: String) =
        findNavController().navigate(PlayersFragmentDirections.actionNavigationPlayersToGamesBottomSheetFragment(teamId, teamName))

    private fun initViews() {
        initSearch()
        initRecyclerPlayers()
    }

    private fun initListeners() {
        viewModel.apply {
            players.observe(viewLifecycleOwner) { pagingData ->
                showPlayersState()
                viewLifecycleOwner.lifecycleScope.launch {
                    playersAdapter.submitData(pagingData)
                }
            }

            uiState.observe(viewLifecycleOwner) { uiState ->
                when (uiState) {
                    is PlayersUiState.Success -> {
                        showPlayersState()
                        viewLifecycleOwner.lifecycleScope.launch {
                            playersAdapter.submitData(uiState.players)
                        }
                    }
                    is PlayersUiState.Error -> showErrorState(uiState.message)
                    is PlayersUiState.Warning -> {
                        showWarningState(uiState.message)
                        viewLifecycleOwner.lifecycleScope.launch {
                            playersAdapter.submitData(uiState.cache)
                        }
                    }
                    is PlayersUiState.Loading -> showLoadingState()
                }
            }
        }
        binding.apply {
            viewCustomError.buttonRetry.setOnClickListener { playersAdapter.retry() }
            viewCustomError.buttonClose.setOnClickListener {
                binding.viewCustomError.root.visibility = View.GONE
            }
        }
    }

    private fun initSearch() {
        binding.searchviewPlayers.apply {
            addTransitionListener { searchView, previousState, newState ->
                if (newState === TransitionState.SHOWING) {
                    binding.textView6.visibility = View.GONE
                }
                if (newState === TransitionState.HIDDEN) {
                    val transition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
                    TransitionManager.beginDelayedTransition(binding.root, transition)
                    binding.textView6.visibility = View.VISIBLE
                }
            }
            editText.setOnEditorActionListener { _, _, _ ->
                val query = binding.searchviewPlayers.text.toString()
                viewModel.searchPlayers(query)
                binding.searchbarPlayers.setText(query)
                binding.searchviewPlayers.hide()
                true
            }
        }
    }

    private fun initRecyclerPlayers() {
        playersAdapter = PlayersAdapter(this)

        playersAdapter.addLoadStateListener { loadState ->
            when(loadState.refresh) {
                is LoadState.Loading -> {
                    showLoadingState()
                    DebugUtils.reportDebug("refresh loading")
                }
                is LoadState.Error -> {
                    val error = (loadState.refresh as LoadState.Error).error
                    val uiMessage = viewModel.mapPagingError(error)
                    if(playersAdapter.itemCount>0){
                        showWarningState(uiMessage)
                    } else {
                        showErrorState(uiMessage)
                    }
                    DebugUtils.reportDebug("refresh error")
                }
                is LoadState.NotLoading -> {
                    showPlayersState()
                    DebugUtils.reportDebug("refresh not loading")
                }
            }
            when(loadState.append){
                is LoadState.Loading -> {
                    DebugUtils.reportDebug("append loading")
                }

                is LoadState.Error -> {
                    DebugUtils.reportDebug("append error")
                }
                is LoadState.NotLoading -> {
                    DebugUtils.reportDebug("append not loading")
                }
            }
            when(loadState.prepend){
                is LoadState.Error -> {
                    DebugUtils.reportDebug("prepend error")
                }
                LoadState.Loading -> {
                    DebugUtils.reportDebug("prepend loading")
                }
                is LoadState.NotLoading -> {
                    DebugUtils.reportDebug("prepend not loading")
                }
            }
            when(loadState.source.refresh){
                is LoadState.Error -> {
                    DebugUtils.reportDebug("refresh source error")
                }
                LoadState.Loading -> {
                    DebugUtils.reportDebug("refresh source loading")
                }
                is LoadState.NotLoading -> {
                    DebugUtils.reportDebug("refresh source not loading")
                }
            }
            when(loadState.source.append){
                is LoadState.Error -> {
                    DebugUtils.reportDebug("append source error")
                }
                LoadState.Loading -> {
                    DebugUtils.reportDebug("append source loading")
                }
                is LoadState.NotLoading -> {
                    DebugUtils.reportDebug("append source not loading")
                }
            }
            when(loadState.source.prepend){
                is LoadState.Error -> {
                    DebugUtils.reportDebug("prepend source error")
                }
                LoadState.Loading -> {
                    DebugUtils.reportDebug("prepend source loading")
                }
                is LoadState.NotLoading -> {
                    DebugUtils.reportDebug("prepend source not loading")
                }
            }
        }

        binding.recyclerviewPlayers.adapter = playersAdapter.withLoadStateHeaderAndFooter(
            header = LoadingAdapter(playersAdapter::retry, viewModel::mapPagingError),
            footer = LoadingAdapter(playersAdapter::retry, viewModel::mapPagingError)
        )
    }
    private fun showWarning(message: String) {
        val snackbarMsg = getString(R.string.combined_msg_offline_mode, message)
        Snackbar
            .make(binding.root, snackbarMsg, Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") { playersAdapter.retry() }
            .show()
    }
    private fun showLoadingState() {
        binding.apply {
            loadingIndicatorPlayers.visibility = View.VISIBLE
            recyclerviewPlayers.visibility = View.GONE
            viewCustomError.root.visibility = View.GONE
        }
    }

    private fun showErrorState(message: String?) {
        binding.apply {
            loadingIndicatorPlayers.visibility = View.GONE
            viewCustomError.root.visibility = View.VISIBLE
            viewCustomError.textErrorMessage.text = message
        }
    }

    private fun showPlayersState() {
        binding.apply {
            loadingIndicatorPlayers.visibility = View.GONE
            viewCustomError.root.visibility = View.GONE
            recyclerviewPlayers.visibility = View.VISIBLE
        }
    }

    private fun showWarningState(message: String) {
        showWarning(message)
        showPlayersState()
        showErrorState(message)
    }
}