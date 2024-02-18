package com.example.lucid.feature.home.community

import com.example.lucid.model.community.CommunityResponse

data class CommunityUiState(
    val posts: List<CommunityResponse>
)