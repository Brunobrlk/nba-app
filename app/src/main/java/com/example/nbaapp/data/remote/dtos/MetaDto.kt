package com.example.nbaapp.data.remote.dtos

import com.squareup.moshi.Json

data class MetaDto(
    @Json(name = "prev_cursor") val previousCursor: Int?,
    @Json(name = "next_cursor") val nextCursor: Int?,
    @Json(name = "per_page") val perPage: Int?,
)