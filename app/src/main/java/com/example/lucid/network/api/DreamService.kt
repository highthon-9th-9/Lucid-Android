package com.example.lucid.network.api

import com.example.lucid.model.dream.DreamPostRequest
import com.example.lucid.model.dream.DreamRequest
import com.example.lucid.model.dream.DreamResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface DreamService {
    @POST("v1/interpretation")
    suspend fun getDream(
        @Body dreamRequest: DreamRequest
    ): DreamResponse

    @POST("v1/posts")
    suspend fun postDream(
        @Header("EMAIL") email: String,
        @Body dreamPostRequest: DreamPostRequest
    )
}