package com.android.rnews.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RedditRepository @Inject constructor(private val redditPagingSource: RedditPagingSource) {
    fun getTopPosts(pageSize: Int): Flow<PagingData<RedditPost>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { redditPagingSource }
        ).flow
    }
}