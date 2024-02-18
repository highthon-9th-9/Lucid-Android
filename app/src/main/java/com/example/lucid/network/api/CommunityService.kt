package com.example.lucid.network.api

import com.example.lucid.model.community.CommunitiesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CommunityService {
    @GET("v1/posts")
    suspend fun getPosts(
        @Query("type") type: String
    ): CommunitiesResponse
}