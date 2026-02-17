package com.example.nbaapp.data.remote.dtos

import com.squareup.moshi.Json

data class MetaDto(
    @field:Json(name = "prev_cursor") val previousCursor: Int?,
    @field:Json(name = "next_cursor") val nextCursor: Int?,
    @field:Json(name = "per_page") val perPage: Int?,
)