package com.example.nbaapp.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class TeamDto(
    @SerializedName("id") val id: Int,

    @SerializedName("full_name") val name: String,

    @SerializedName("city") val city: String,

    @SerializedName("conference") val conference: String
)


