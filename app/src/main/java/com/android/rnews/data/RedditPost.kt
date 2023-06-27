package com.android.rnews.data

import com.squareup.moshi.Json

data class RedditPost(
    @Json(name = "author") val author: String,
    @Json(name = "created_utc") val createdUtc: Long,
    @Json(name = "thumbnail") val thumbnail: String
)