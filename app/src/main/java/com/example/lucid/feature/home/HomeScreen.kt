package com.example.lucid.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lucid.R
import com.example.lucid.feature.home.community.CommunityScreen
import com.example.lucid.feature.home.info.InfoScreen
import com.example.lucid.ui.theme.Typography
import com.example.lucid.ui.theme.backGround
import com.example.lucid.ui.theme.darkWhite
import com.example.lucid.ui.theme.gray
import com.example.lucid.ui.theme.gray200
import com.example.lucid.ui.theme.gray300
import com.example.lucid.ui.theme.main
import com.example.lucid.ui.theme.mainPurple
import com.example.lucid.ui.theme.pretendard
import com.example.lucid.ui.theme.white
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var isDream by remember { mutableStateOf(true) }
    var isCommunity by remember { mutableStateOf(false) }

    var text by remember { mutableStateOf("") }

    var columnHeightPx by remember {
        mutableStateOf(0f)
    }

    val event = viewModel.event

    LaunchedEffect(event) {
        event.collect {
            when (it) {
                is HomeViewModel.Event.NavigateToResult -> {
                    val encodedUrl = URLEncoder.encode(it.image, StandardCharsets.UTF_8.toString())
                    navController.navigate("result/${it.data}/$encodedUrl")
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backGround),
        contentAlignment = Alignment.BottomEnd,
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            var showDivider by remember { mutableStateOf(true) }

            showDivider = isDream

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                isDream = true
                                isCommunity = false
                            }
                        ),
                    text = "꿈 해몽",
                    fontFamily = pretendard,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDream) Color.White else gray200,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                isCommunity = true
                                isDream = false
                            }
                        ),
                    text = "커뮤니티",
                    fontFamily = pretendard,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isCommunity) Color.White else gray200,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                AsyncImage(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(100)),
                    model = "https://all.image.mycelebs.com/578/578_573@372.jpg",
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            if (showDivider) {
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = gray
                )
            }

            if (isDream) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.Top)
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        AsyncImage(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(100)),
                            model = "https://all.image.mycelebs.com/578/578_573@372.jpg",
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }


                    TextField(
                        modifier = Modifier.align(Alignment.Top),
                        value = text,
                        onValueChange = { text = it },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedTextColor = darkWhite,
                            unfocusedTextColor = darkWhite,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = {
                            Text(
                                text = "어떤 꿈을 꾸셨나요?",
                                style = Typography.bodySmall,
                                color = gray300
                            )
                        }
                    )
                }
            }

            if (isCommunity) {
                ViewPager()
            }
        }

        if (isDream) {
            Column(
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .clickable {
                            viewModel.getDream(text)
                        }
                        .onGloballyPositioned {
                            columnHeightPx = it.boundsInWindow().bottom
                        }
                        .background(mainPurple, RoundedCornerShape(100))
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.height(20.dp),
                        painter = painterResource(id = if (columnHeightPx < 2295) R.drawable.ic_mic else R.drawable.ic_moon),
                        contentDescription = null,
                        tint = white
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = if (columnHeightPx < 2275) "음성입력" else "해몽하기" ,
                        style = Typography.bodyMedium,
                        color = white
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
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
                        thickness = 0.5.dp,
                        color = gray,
                    )
                },
                indicator = { tabPositions ->
                    if (currentPage < tabPositions.size) {
                        TabRowDefaults.Indicator(
                            modifier = Modifier
                                .width(50.dp)
                                .tabIndicatorOffset(tabPositions[currentPage]),
                            color = main
                        )
                    }
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
                                fontSize = 16.sp,
                                fontFamily = pretendard,
                                fontWeight = FontWeight.SemiBold
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
