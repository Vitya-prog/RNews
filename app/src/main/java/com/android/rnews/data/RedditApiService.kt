package com.android.rnews.data

import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApiService {
    @GET("/top.json")
    suspend fun getTopPosts(
        @Query("limit") limit: Int,
        @Query("after") after: String? = null
    ): RedditApiResponse
}