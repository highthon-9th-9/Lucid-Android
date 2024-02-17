package com.example.lucid.network.api

import com.example.lucid.model.community.CommunityResponse
import com.example.lucid.model.login.LoginRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("api/v1/auth")
    suspend fun login(
        @Body loginRequest: LoginRequest
    )

    @GET("api/v1/posts?type=community")
    suspend fun getCommunity(
        @Path("type") type: String,
    ): List<CommunityResponse>
}