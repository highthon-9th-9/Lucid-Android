package com.example.lucid.feature.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lucid.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                         Icon(
                             imageVector = Icons.Filled.ArrowBack,
                             contentDescription = "back",
                         )
                    }
                },
                title = {
                    Text(text = "김종윤님의 페이지")
                },
            )
        },
    ) {paddingValues ->  
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize(),
        ) {
            Image(
                modifier = Modifier.padding(12.dp),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "profile"
            )
            Text(
                modifier = Modifier
                    .padding(
                        top = 12.dp,
                        start = 28.dp,
                    ),
                text = "나의 꿈 일기"
            )
            Spacer(modifier = Modifier.padding(top = 12.dp))
            Diary()
        }
    }
}

@Composable
fun Diary() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 11.dp,
            ),
    ) {

    }
    LazyVerticalGrid(
        contentPadding = PaddingValues(),
        columns = GridCells.Adaptive(minSize = 128.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        //items()
    }
}

