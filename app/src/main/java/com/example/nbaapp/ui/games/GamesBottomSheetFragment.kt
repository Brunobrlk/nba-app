package com.example.nbaapp.ui.games

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.nbaapp.R
import com.example.nbaapp.databinding.BottomsheetGamesBinding
import com.example.nbaapp.domain.models.Game
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class GamesBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetGamesBinding
    private lateinit var gamesAdapter: GamesAdapter
    private val viewModel: GamesViewModel by viewModels()
    private val args: GamesBottomSheetFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, /* forward = */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, /* forward = */ false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): BottomSheetDialog {
        val dialog = BottomSheetDialog(requireContext(), R.style.FullScreenBottomSheetDialog)
        dialog.setOnShowListener { d ->
            val d = dialog
            val sheet = d.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)

            sheet.let { bottom ->
                // Make the sheet take the entire screen height
                bottom!!.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                bottom.requestLayout()

                val behavior = BottomSheetBehavior.from(bottom).apply {
                    // Allow full expansion
                    isFitToContents = false
                    expandedOffset = 0
                    // Start expanded
                    state = BottomSheetBehavior.STATE_EXPANDED
                    // Allow dragging/dismiss by swipe
                    isDraggable = true
                    // no peek
                    peekHeight = Resources.getSystem().displayMetrics.heightPixels
                }
            }

        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.loadGames(args.teamId)
        }
        initViews()
        initListeners()
    }

    private fun initListeners() {
        binding.buttonReturn.setOnClickListener { dismiss() }

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is GamesUiState.Success -> showGamesState(uiState.games)
                is GamesUiState.Error -> showErrorState(uiState.message)
                is GamesUiState.Loading -> showLoadingState()
            }
        }

        binding.viewCustomError.buttonRetry.setOnClickListener { viewModel.loadGames(args.teamId) }
        binding.viewCustomError.buttonClose.setOnClickListener { dismiss() }
    }

    private fun showLoadingState() {
        binding.apply {
            progressBar3.visibility = View.VISIBLE
            recyclerviewGames.visibility = View.GONE
            viewCustomError.root.visibility = View.GONE
        }
    }

    private fun showErrorState(message: String) {
        binding.apply {
            progressBar3.visibility = View.GONE
            recyclerviewGames.visibility = View.GONE
            viewCustomError.root.visibility = View.VISIBLE
            viewCustomError.textErrorMessage.text = message
        }
    }

    private fun showGamesState(games: List<Game>) {
        binding.apply {
            progressBar3.visibility = View.GONE
            recyclerviewGames.visibility = View.VISIBLE
            viewCustomError.root.visibility = View.GONE
        }
        val gamesListItem = buildList {
            add(GameListItem.Header("Games"))
            games.forEach { add(GameListItem.GameRow(it)) }
        }
        gamesAdapter.submitList(gamesListItem)
    }

    private fun initViews() {
        binding.textviewTeamTitle.text = args.teamName

        initRecycler()
    }

    private fun initRecycler() {
        gamesAdapter = GamesAdapter()
        binding.recyclerviewGames.adapter = gamesAdapter
    }
}