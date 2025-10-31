package com.example.nbaapp.data.mappers

fun normalizeBlank(value: String?): String? =
    value?.trim()?.takeIf { it.isNotEmpty() }