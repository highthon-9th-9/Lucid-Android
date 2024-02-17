package com.example.lucid.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lucid.R
import com.example.lucid.feature.home.community.CommunityScreen
import com.example.lucid.feature.home.info.InfoScreen
import com.example.lucid.ui.theme.backGround
import com.example.lucid.ui.theme.main
import com.example.lucid.ui.theme.white

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backGround),
        contentAlignment = Alignment.BottomEnd,
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBar()
            ViewPager()
        }
        AddBtn()
    }
}

@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Image(
            modifier = Modifier
                .clickable(onClick = { }),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "profile",
        )
        Image(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(id = R.drawable.ic_app_logo),
            contentDescription = "app_logo",
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ViewPager() {
    val tabs = listOf("커뮤니티", "정보")
    var currentPage by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TabRow(
                selectedTabIndex = currentPage,
                divider = {
                    Divider(
                        thickness = 2.dp,
                        color = Color.Black,
                    )
                }

            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        modifier = Modifier.background(backGround),
                        selected = currentPage == index,
                        onClick = {
                            currentPage = index
                        },
                        text = {
                            Text(
                                text = title,
                                color = white,
                            )
                        },
                    )
                }

            }
        },
        content = {
            when (currentPage) {
                0 -> CommunityScreen(it)
                1 -> InfoScreen(it)
            }
        }
    )
}

@Composable
fun AddBtn() {
    IconButton(
        modifier = Modifier
            .size(100.dp)
            .padding(
                bottom = 33.dp,
                end = 15.dp,
            )
            .clip(CircleShape)
            .background(
                //shape = CircleShape,
                color = main
            ),
        onClick = { /*TODO*/ }
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "addBtn",
            tint = white,
        )
    }
}
