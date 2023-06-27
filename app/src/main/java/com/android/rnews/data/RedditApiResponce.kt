package com.android.rnews.data

import com.squareup.moshi.Json

data class RedditApiResponse(
    @Json(name = "data") val data: RedditDataResponse
)

data class RedditDataResponse(
    @Json(name = "children") val children: List<RedditChildrenResponse>
)

data class RedditChildrenResponse(
    @Json(name = "data") val post: RedditPost
)