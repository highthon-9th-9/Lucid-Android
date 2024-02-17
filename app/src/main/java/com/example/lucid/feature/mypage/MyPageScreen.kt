package com.example.lucid.feature.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
        }
    }
}

