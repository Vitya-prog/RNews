package com.android.rnews.data

import retrofit2.http.GET

interface RedditApiService {
    @GET("top.json")
    suspend fun getTopPosts(): RedditApiResponse
}