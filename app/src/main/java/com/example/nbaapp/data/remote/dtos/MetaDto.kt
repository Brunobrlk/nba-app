package com.example.nbaapp.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class MetaDto(
    @SerializedName("prev_cursor") val previousCursor: Int?,
    @SerializedName("next_cursor") val nextCursor: Int?,
    @SerializedName("per_page") val perPage: Int?,
)