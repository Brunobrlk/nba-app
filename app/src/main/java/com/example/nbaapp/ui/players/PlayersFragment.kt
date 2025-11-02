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
import androidx.transition.TransitionManager
import com.example.nbaapp.core.utils.DebugUtils
import com.example.nbaapp.databinding.FragmentPlayersBinding
import com.example.nbaapp.ui.common.adapter.LoadingAdapter
import com.example.nbaapp.ui.players.adapter.OnPlayerClick
import com.example.nbaapp.ui.players.adapter.PlayersAdapter
import com.google.android.material.search.SearchView.TransitionState
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
                is LoadState.Loading -> showLoadingState()
                is LoadState.NotLoading -> showPlayersState()
                is LoadState.Error -> {
                    val error = (loadState.refresh as LoadState.Error).error
                    val uiMessage = viewModel.mapPagingError(error)
                    if(playersAdapter.itemCount>0){
                        showWarningState(uiMessage)
                    } else {
                        showErrorState(uiMessage)
                    }
                }
            }
        }

        binding.recyclerviewPlayers.adapter = playersAdapter.withLoadStateHeaderAndFooter(
            header = LoadingAdapter(playersAdapter::retry, viewModel::mapPagingError),
            footer = LoadingAdapter(playersAdapter::retry, viewModel::mapPagingError)
        )
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
        showPlayersState()
        showErrorState(message)
    }
}