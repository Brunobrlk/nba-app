package com.example.nbaapp.domain.models

data class Team (
    val id: Int = 0,
    val name: String,
    val city: String,
    val conference: String
)