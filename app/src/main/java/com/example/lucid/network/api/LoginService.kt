package com.example.lucid.network.api

import com.example.lucid.model.login.LoginRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @POST("v1/auth")
    suspend fun login(
        @Body loginRequest: LoginRequest
    )
}