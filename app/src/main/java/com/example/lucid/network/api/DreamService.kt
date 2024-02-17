package com.example.lucid.network.api

import com.example.lucid.model.dream.DreamRequest
import com.example.lucid.model.dream.DreamResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface DreamService {
    @POST("v1/interpretation")
    suspend fun getDream(
        @Body dreamRequest: DreamRequest
    ): DreamResponse
}