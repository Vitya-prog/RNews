package com.android.rnews.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import javax.inject.Inject

class RedditPagingSource @Inject constructor(private val redditApiService: RedditApiService) : PagingSource<String, RedditPost>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, RedditPost> {
        return try {
            val currentLoadingPageKey = params.key ?: ""
            val response = redditApiService.getTopPosts(params.loadSize, currentLoadingPageKey)
            val responseData = mutableListOf<RedditPost>()
            val data = response.data.children.map { it.post }
            responseData.addAll(data)
            val nextKey = response.data.after
            LoadResult.Page(
                data = responseData,
                prevKey = null, // Reddit API does not support going backwards
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, RedditPost>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey ?: state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }
}