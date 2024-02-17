package com.example.lucid.model.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("profileImage")
    val profileImage: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
)
