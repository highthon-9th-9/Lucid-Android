package com.example.lucid.feature.mypage

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lucid.R
import com.example.lucid.feature.home.community.CommunityItem
import com.example.lucid.ui.theme.Typography
import com.example.lucid.ui.theme.backGround
import com.example.lucid.ui.theme.darkWhite
import com.example.lucid.ui.theme.gray
import com.example.lucid.ui.theme.pretendard
import com.example.lucid.ui.theme.white
import com.kakao.sdk.user.UserApiClient

@Composable
fun MyPageScreen(
    navController: NavController,
    viewModel: MyPageViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var profileImage by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "사용자 추가 정보 획득 실패", error)
                return@me
            } else if (user != null) {
                profileImage = user.kakaoAccount!!.profile!!.profileImageUrl!!
                name = user.kakaoAccount!!.profile!!.nickname!!
                viewModel.getPosts(name)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backGround),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            navController.popBackStack()
                        }
                    )
                    .align(Alignment.CenterStart)
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_left_arrow),
                contentDescription = null,
                tint = darkWhite
            )

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "마이페이지",
                style = Typography.bodyMedium.copy(fontSize = 16.sp),
                color = darkWhite
            )
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(backGround),
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(12.dp))

                    AsyncImage(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100))
                            .size(100.dp),
                        model = profileImage,
                        contentDescription = null,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = name,
                        color = white,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Divider(modifier = Modifier.fillMaxWidth(), color = gray)

                    Spacer(modifier = Modifier.height(12.dp))

                }

            }

            items(uiState.posts.size) {
                CommunityItem(
                    content = uiState.posts[it].input,
                    profileImage = uiState.posts[it].profileImage,
                    author = uiState.posts[it].author,
                    contentImage = uiState.posts[it].image
                )
            }
        }
    }
}
