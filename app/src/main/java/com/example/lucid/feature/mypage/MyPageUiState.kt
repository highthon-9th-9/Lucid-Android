package com.example.lucid.feature.mypage

import com.example.lucid.model.community.CommunityResponse

data class MyPageUiState(
    val posts: List<CommunityResponse>
)