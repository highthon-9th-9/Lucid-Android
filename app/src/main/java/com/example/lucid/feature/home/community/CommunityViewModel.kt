package com.example.lucid.feature.home.community

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lucid.feature.login.LoginViewModel
import com.example.lucid.model.community.CommunityResponse
import com.example.lucid.network.Retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CommunityViewModel : ViewModel() {
    private val communityService = Retrofit.communityService

    private val _uiState = MutableStateFlow(CommunityUiState(listOf()))
    val uiState = _uiState.asStateFlow()


    fun getCommunity() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            communityService.getPosts("community")
        }.onSuccess { response ->
            _uiState.update {
                it.copy(
                    posts = response.data
                )
            }
        }.onFailure {
            Log.d("ERROR: ", it.message.toString())
        }
    }
}