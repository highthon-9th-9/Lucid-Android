package com.example.lucid.feature.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lucid.network.Retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyPageViewModel : ViewModel() {

    private val communityService = Retrofit.communityService

    private val _uiState = MutableStateFlow(MyPageUiState(listOf()))
    val uiState = _uiState.asStateFlow()


    fun getPosts(author: String) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            communityService.getPosts("community")
        }.onSuccess { response ->
            _uiState.update {
                it.copy(
                    posts = response.data.filter { communityResponse ->
                        communityResponse.author == author
                    }
                )
            }
        }.onFailure {
            Log.d("ERROR: ", it.message.toString())
        }
    }
}