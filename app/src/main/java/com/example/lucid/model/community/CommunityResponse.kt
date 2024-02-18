package com.example.lucid.model.community

import com.example.lucid.model.User
import com.google.gson.annotations.SerializedName

data class CommunityResponse(
    val id: Long,
    val type: String,
    val data: String,
    val image: String,
    val input: String,
    val author: String,
    @SerializedName("profile_image")
    val profileImage: String,
)
