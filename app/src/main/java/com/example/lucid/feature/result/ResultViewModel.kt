package com.example.lucid.feature.result

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lucid.model.dream.DreamPostRequest
import com.example.lucid.model.dream.DreamRequest
import com.example.lucid.network.Retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ResultViewModel : ViewModel() {
    private val dreamService = Retrofit.dreamService

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    fun postDream(
        email: String,
        data: String,
        input: String,
        image: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                dreamService.postDream(
                    email,
                    DreamPostRequest(
                        data = data,
                        input = input,
                        image = image
                    )
                )
            }.onSuccess {
                _event.emit(Event.NavigateToCommunity)
            }.onFailure {
                Log.e("ERROR", it.message.toString())
            }
        }
    }

    sealed interface Event {
        data object NavigateToCommunity : Event
    }
}