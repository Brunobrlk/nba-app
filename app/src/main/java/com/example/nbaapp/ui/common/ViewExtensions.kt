package com.example.nbaapp.ui.common

import android.widget.TextView

fun TextView.setTextOrDash(value: String?) {
    text = value ?: "—"
}