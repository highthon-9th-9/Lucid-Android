package com.example.lucid.feature.home.info

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

class InfoViewModel : ViewModel() {
    private val communityService = Retrofit.communityService

    private val _uiState = MutableStateFlow(InfoUiState(listOf()))
    val uiState = _uiState.asStateFlow()


    fun getInfo() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            communityService.getPosts("info")
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