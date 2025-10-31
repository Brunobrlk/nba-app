package com.example.nbaapp.ui.common.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import com.example.nbaapp.R
import com.example.nbaapp.data.local.database.utils.SortTeamBy
import com.example.nbaapp.databinding.DialogSortbyBinding
import com.example.nbaapp.core.helpers.Constants
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SortByDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogSortbyBinding.inflate(layoutInflater)
        binding.apply {
            buttonClose.setOnClickListener {
                dismiss()
            }
            buttonApply.setOnClickListener {
                val sort = when (binding.toggleButton.checkedButtonId) {
                    R.id.button_by_name -> SortTeamBy.NAME
                    R.id.button_by_city -> SortTeamBy.CITY
                    R.id.button_by_conference -> SortTeamBy.CONFERENCE
                    View.NO_ID -> SortTeamBy.NAME
                    else -> SortTeamBy.NAME
                }

                val isAscending =
                    binding.radioGroup.checkedRadioButtonId == R.id.radiobutton_ascending

                setFragmentResult(
                    Constants.SORT_RESULT_KEY, bundleOf(
                        Constants.SORT_KEY to sort.name, Constants.IS_ASCENDING_KEY to isAscending
                    )
                )
                dismiss()
            }
        }

        return MaterialAlertDialogBuilder(requireActivity()).setView(binding.root).create()
    }

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, null)
    }
}