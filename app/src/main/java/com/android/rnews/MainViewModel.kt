package com.android.rnews

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.android.rnews.data.RedditPost
import com.android.rnews.data.RedditRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val redditRepository: RedditRepository,
    val networkConnection: NetworkConnection) : ViewModel() {
    fun getTopPosts(pageSize: Int): Flow<PagingData<RedditPost>> {
        return redditRepository.getTopPosts(pageSize)
    }
}