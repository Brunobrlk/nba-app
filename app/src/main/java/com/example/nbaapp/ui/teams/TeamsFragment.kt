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
import com.example.nbaapp.domain.helpers.Constants
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
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is TeamsUiState.Loading -> showLoadingState()
                is TeamsUiState.Success -> showTeamsState(uiState.teams)
                is TeamsUiState.Warning -> {
                    showTeamsState(uiState.data)
                    showErrorState(uiState.message)
                }

                is TeamsUiState.Error -> showErrorState(uiState.message)
            }
        }

        viewModel.currentSort.observe(viewLifecycleOwner) { sort ->
            val sortText = when (sort) {
                SortTeamBy.NAME -> "Name"
                SortTeamBy.CITY -> "City"
                SortTeamBy.CONFERENCE -> "Conference"
            }
            binding.buttonSortBy.text = sortText
        }

        setFragmentResultListener(Constants.SORT_RESULT_KEY) { _, bundle ->
            val sort =
                SortTeamBy.valueOf(bundle.getString(Constants.SORT_KEY) ?: SortTeamBy.NAME.name)
            val isAscending = bundle.getBoolean(Constants.IS_ASCENDING_KEY)
            viewModel.getTeamsOrdered(sort, isAscending)
        }

        binding.buttonSortBy.setOnClickListener {
            SortByDialog().show(parentFragmentManager)
        }

        binding.viewCustomError.apply {
            buttonRetry.setOnClickListener {
                viewModel.getTeams()
            }
            binding.viewCustomError.buttonClose.setOnClickListener {
                binding.viewCustomError.root.visibility = View.GONE
            }
        }
    }

    private fun initViews() {
        initRecyclerTeams()
    }

    private fun initRecyclerTeams() {
        teamsAdapter = TeamsAdapter(this)
        binding.recyclerviewTeams.adapter = teamsAdapter
    }

    private fun showTeamsState(teams: List<Team>) {
        binding.apply {
            progressBar.visibility = View.GONE
            recyclerviewTeams.visibility = View.VISIBLE
            viewCustomError.root.visibility = View.GONE
        }
        val teamsListItem = buildList {
            add(TeamListItem.Header("Teams"))
            teams.forEach { add(TeamListItem.TeamRow(it)) }
        }
        teamsAdapter.submitList(teamsListItem)
    }

    private fun showLoadingState() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            recyclerviewTeams.visibility = View.GONE
            viewCustomError.root.visibility = View.GONE
        }
    }

    private fun showErrorState(message: String) {
        binding.apply {
            progressBar.visibility = View.GONE
            viewCustomError.root.visibility = View.VISIBLE
            viewCustomError.textErrorMessage.text = message
        }
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