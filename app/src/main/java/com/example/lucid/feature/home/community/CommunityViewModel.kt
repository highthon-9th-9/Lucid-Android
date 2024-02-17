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
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CommunityViewModel : ViewModel() {
    private val apiService = Retrofit.apiService

    private val _communityState = MutableLiveData(listOf<CommunityResponse>())
    val communityState : LiveData<List<CommunityResponse>> = _communityState

    fun getCommunity(
        type: String,
    ) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            apiService.getCommunity(
                type = type,
            )
        }.onSuccess {
            _communityState.value = it
        }.onFailure {
            Log.d("ERROR: ", it.message.toString())
        }
    }
}