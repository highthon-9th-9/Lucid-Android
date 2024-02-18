package com.example.lucid.feature.home.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.lucid.R
import com.example.lucid.ui.theme.Typography
import com.example.lucid.ui.theme.backGround
import com.example.lucid.ui.theme.darkWhite
import com.example.lucid.ui.theme.gray

@Composable
fun InfoScreen(
    paddingValues: PaddingValues,
    viewModel: InfoViewModel = viewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.getInfo()
    }

    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
            .background(backGround),
    ) {
        items(uiState.posts.size) {
            InfoItem(
                content = uiState.posts[it].data,
                profileImage = uiState.posts[it].profileImage,
                author = uiState.posts[it].author,
                contentImage = uiState.posts[it].image
            )
        }
    }
}

@Composable
fun InfoItem(
    profileImage: String,
    content: String,
    author: String,
    contentImage: String?
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp,
                )
                .background(backGround),
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(100)),
                model = profileImage,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = author,
                    style = Typography.titleSmall.copy(lineHeight = 19.sp),
                    color = darkWhite,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = content,
                    style = Typography.bodySmall.copy(lineHeight = 19.sp),
                    color = darkWhite,
                )

                contentImage?.let {
                    Spacer(modifier = Modifier.height(4.dp))

                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp)),
                        model = contentImage,
                        contentDescription = null,
                    )
                }
            }
        }
        Divider(modifier = Modifier.fillMaxWidth(),
            color = gray
        )
    }
}

