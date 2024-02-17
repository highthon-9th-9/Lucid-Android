package com.example.lucid.feature.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lucid.model.login.LoginRequest
import com.example.lucid.network.Retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val loginService = Retrofit.loginService

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    fun login(
        profileImage: String?,
        name: String,
        email: String,
    ) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            loginService.login(
                LoginRequest(
                    profileImage,
                    name,
                    email
                )
            )
        }.onSuccess {
            _event.emit(Event.NavigateToMain)
        }.onFailure {
            Log.d("ERROR: ", it.message.toString())
        }
    }

    sealed interface Event {
        data object NavigateToMain : Event
    }
}