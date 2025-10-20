package com.example.nbaapp.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class PlayerDto(
    @SerializedName("id") val id: Int,

    @SerializedName("first_name") val firstName: String,

    @SerializedName("last_name") val lastName: String,

    @SerializedName("team") val team: TeamDto
)