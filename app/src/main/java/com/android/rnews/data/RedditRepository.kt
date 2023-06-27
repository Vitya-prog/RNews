package com.android.rnews.data

import javax.inject.Inject

class RedditRepository @Inject constructor(private val redditApiService: RedditApiService) {


    suspend fun getTopPosts(): List<RedditPost> {
        return redditApiService.getTopPosts().data.children.map { it.post }
    }
}