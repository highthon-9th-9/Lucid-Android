package com.example.lucid.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lucid.model.dream.DreamRequest
import com.example.lucid.network.Retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val dreamService = Retrofit.dreamService

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    fun getDream(data: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                dreamService.getDream(DreamRequest(data))
            }.onSuccess {
                _event.emit(Event.NavigateToResult(data = it.data, image = it.image))
            }.onFailure {
                Log.e("ERROR", it.message.toString())
            }
        }
    }

    sealed interface Event {
        data class NavigateToResult(
            val data: String,
            val image: String,
        ) : Event
    }
}