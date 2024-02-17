package com.example.lucid.feature.result

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lucid.R
import com.example.lucid.ui.theme.Typography
import com.example.lucid.ui.theme.backGround
import com.example.lucid.ui.theme.darkWhite
import com.example.lucid.ui.theme.main
import com.example.lucid.ui.theme.pretendard
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.kakao.sdk.user.UserApiClient

@Composable
fun ResultScreen(
    data: String,
    image: String,
    input: String,
    navController: NavController,
    viewModel: ResultViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var show by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    val event = viewModel.event

    var email by remember { mutableStateOf("") }

    LaunchedEffect(event) {
        event.collect {
            when (it) {
                is ResultViewModel.Event.NavigateToCommunity -> {
                    navController.navigate("home")
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "사용자 추가 정보 획득 실패", error)
                return@me
            } else if (user != null) {
                email = user.kakaoAccount!!.email!!
            }
        }
    }

    if (show) {
        BottomSheetDialog(
            onDismissRequest = { show = false }
        ) {
            Surface(
                color = backGround,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "주의사항",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = pretendard,
                        color = darkWhite
                    )
                    Text(
                        text = "AI 꿈 해몽은 지극히 주관적일 수 있습니다.\n" +
                                "사용에 주의 요함",
                        style = Typography.bodySmall,
                        color = darkWhite
                    )

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        onClick = {
                            show = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = main)
                    ) {
                        Text(
                            text = "확인",
                            color = Color.White,
                            style = Typography.bodyMedium
                        )
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backGround)
                .padding(16.dp)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
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
                        text = "나의 꿈 해석 결과",
                        style = Typography.bodyMedium.copy(fontSize = 16.sp),
                        color = darkWhite
                    )

                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(24.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    show = true
                                }
                            ),
                        painter = painterResource(id = R.drawable.ic_error),
                        contentDescription = null,
                        tint = darkWhite
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {

                Spacer(modifier = Modifier.height(8.dp))

                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    model = image,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = data,
                    style = Typography.bodySmall.copy(lineHeight = 20.sp),
                    color = darkWhite
                )

                Spacer(modifier = Modifier.height(64.dp))
            }


        }

        Column(modifier = Modifier.align(Alignment.BottomCenter).padding(horizontal = 16.dp)) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = {
                    viewModel.postDream(email, data, input, image)
                },
                colors = ButtonDefaults.buttonColors(containerColor = main),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "업로드",
                    color = Color.White,
                    style = Typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}