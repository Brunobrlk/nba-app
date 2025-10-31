package com.example.nbaapp.data.local.database.utils

import androidx.annotation.StringRes
import com.example.nbaapp.R

enum class SortTeamBy(@StringRes val label: Int) {
    NAME(R.string.label_sort_by_name),
    CITY(R.string.label_sort_by_city),
    CONFERENCE(R.string.label_sort_by_conference)
}