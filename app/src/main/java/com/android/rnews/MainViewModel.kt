package com.android.rnews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.rnews.data.RedditPost
import com.android.rnews.data.RedditRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val redditRepository: RedditRepository) : ViewModel() {

val posts = MutableLiveData<List<RedditPost>>()
    fun topPosts() {
        viewModelScope.launch(Dispatchers.Main) {
            posts.value = redditRepository.getTopPosts()
        }
    }
}