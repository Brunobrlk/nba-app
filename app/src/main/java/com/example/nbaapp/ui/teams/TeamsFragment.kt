package com.example.nbaapp.ui.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.nbaapp.data.local.database.utils.SortTeamBy
import com.example.nbaapp.databinding.FragmentTeamsBinding
import com.example.nbaapp.core.utils.Constants
import com.example.nbaapp.domain.models.Team
import com.example.nbaapp.domain.models.TeamListItem
import com.example.nbaapp.ui.common.dialogs.SortByDialog
import com.example.nbaapp.ui.teams.adapter.OnTeamClick
import com.example.nbaapp.ui.teams.adapter.TeamsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamsFragment : Fragment(), OnTeamClick {
    private lateinit var teamsAdapter: TeamsAdapter
    private lateinit var binding: FragmentTeamsBinding
    private val viewModel: TeamsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamsBinding.inflate(inflater, container, false)
        initViews()
        initListeners()
        return binding.root
    }

    private fun initListeners() {
        viewModel.apply {
            uiState.observe(viewLifecycleOwner) { uiState ->
                when (uiState) {
                    is TeamsUiState.Loading -> showLoadingState()
                    is TeamsUiState.Success -> showTeamsState(uiState.teams)
                    is TeamsUiState.Warning -> showWarningState(uiState.cache, uiState.message)
                    is TeamsUiState.Error -> showErrorState(uiState.message)
                }
            }
            currentSort.observe(viewLifecycleOwner) { sort ->
                binding.buttonSortBy.text = getString(sort.label)
            }
        }

        binding.apply {
            buttonSortBy.setOnClickListener {
                SortByDialog().show(parentFragmentManager)
            }

            viewCustomError.apply {
                buttonRetry.setOnClickListener {
                    viewModel.getTeams()
                }
                binding.viewCustomError.buttonClose.setOnClickListener {
                    binding.viewCustomError.root.visibility = View.GONE
                }
            }
        }
        
        setFragmentResultListener(Constants.SORT_RESULT_KEY) { _, bundle ->
            val sort =
                SortTeamBy.valueOf(bundle.getString(Constants.SORT_KEY) ?: SortTeamBy.NAME.name)
            val isAscending = bundle.getBoolean(Constants.IS_ASCENDING_KEY)
            viewModel.getTeamsOrdered(sort, isAscending)
        }
    }

    private fun initViews() {
        initRecyclerTeams()
    }

    private fun initRecyclerTeams() {
        teamsAdapter = TeamsAdapter(this)
        binding.recyclerviewTeams.adapter = teamsAdapter
    }

    private fun showTeamsState(teams: List<TeamListItem>) {
        binding.apply {
            loadingIndicatorTeams.visibility = View.GONE
            recyclerviewTeams.visibility = View.VISIBLE
            viewCustomError.root.visibility = View.GONE
        }
        teamsAdapter.submitList(teams)
    }

    private fun showLoadingState() {
        binding.apply {
            loadingIndicatorTeams.visibility = View.VISIBLE
            recyclerviewTeams.visibility = View.GONE
            viewCustomError.root.visibility = View.GONE
        }
    }

    private fun showErrorState(message: String) {
        binding.apply {
            loadingIndicatorTeams.visibility = View.GONE
            viewCustomError.root.visibility = View.VISIBLE
            viewCustomError.textErrorMessage.text = message
        }
    }

    fun showWarningState(cache: List<TeamListItem>, message: String) {
        showTeamsState(cache)
        showErrorState(message)
    }

    override fun onTeamClick(team: Team) {
        findNavController().navigate(
            TeamsFragmentDirections.actionNavigationHomeToGamesBottomSheetFragment(
                team.id,
                team.name
            )
        )
    }
}