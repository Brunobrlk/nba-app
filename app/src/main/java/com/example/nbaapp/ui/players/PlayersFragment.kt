package com.example.nbaapp.ui.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import com.example.nbaapp.databinding.FragmentPlayersBinding
import com.example.nbaapp.domain.models.Player
import com.google.android.material.search.SearchView.TransitionState
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlayersFragment : Fragment(), OnPlayerClick {
    private lateinit var binding: FragmentPlayersBinding
    private lateinit var playersAdapter: PlayersAdapter
    private val viewModel: PlayersViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPlayersBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        initSearch()
        initListeners()
        initRecyclerPlayers()
    }

    private fun initListeners() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is PlayersUiState.Success -> showPlayersState(uiState.players)
                is PlayersUiState.Error -> showErrorState(uiState.message)
                is PlayersUiState.Warning -> {
                    showPlayersState(uiState.cachedData)
                    showErrorState(uiState.message)
                }
                is PlayersUiState.Loading -> showLoadingState()
            }
        }
        binding.viewCustomError.buttonRetry.setOnClickListener { viewModel.getPlayers() }
        binding.viewCustomError.buttonClose.setOnClickListener {
            binding.viewCustomError.root.visibility = View.GONE
        }
    }

    private fun showLoadingState() {
        binding.apply {
            progressBar2.visibility = View.VISIBLE
            recyclerviewPlayers.visibility = View.GONE
            viewCustomError.root.visibility = View.GONE
        }
    }

    private fun showErrorState(message: String) {
        binding.apply {
            progressBar2.visibility = View.GONE
            viewCustomError.root.visibility = View.VISIBLE
            viewCustomError.textErrorMessage.text = message
        }
    }

    private fun showPlayersState(players: List<Player>) {
        binding.apply {
            progressBar2.visibility = View.GONE
            viewCustomError.root.visibility = View.GONE
            recyclerviewPlayers.visibility = View.VISIBLE
        }
        val playersListItem = buildList {
            add(PlayerListItem.Header("Players"))
            players.forEach { add(PlayerListItem.PlayerRow(it)) }
        }
        playersAdapter.submitList(playersListItem)
    }

    private fun initSearch() {
        binding.searchviewPlayers.addTransitionListener { searchView, previousState, newState ->
            if (newState === TransitionState.SHOWING) {
                binding.textView6.visibility = View.GONE
            }
            if (newState === TransitionState.HIDDEN) {
                val transition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
                TransitionManager.beginDelayedTransition(binding.root, transition)
                binding.textView6.visibility = View.VISIBLE
            }
        }
        binding.searchviewPlayers.editText.setOnEditorActionListener { _, _, _ -> 
            val query = binding.searchviewPlayers.text.toString()
            viewModel.searchPlayers(query)
            binding.searchbarPlayers.setText(query)
            binding.searchviewPlayers.hide()
            true
        }
    }

    private fun initRecyclerPlayers() {
        playersAdapter = PlayersAdapter(this)
        binding.recyclerviewPlayers.adapter = playersAdapter
    }

    override fun onPlayerClick(teamId: Int, teamName: String) {
        findNavController().navigate(PlayersFragmentDirections.actionNavigationPlayersToGamesBottomSheetFragment(teamId, teamName))
    }
}