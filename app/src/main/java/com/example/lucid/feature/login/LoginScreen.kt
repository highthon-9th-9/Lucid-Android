package com.example.lucid.feature.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lucid.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.kakao.sdk.user.UserApiClient

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel()
) {
    val event = viewModel.event
    val context = LocalContext.current

    LaunchedEffect(event) {
        event.collect {
            when (it) {
                is LoginViewModel.Event.NavigateToMain -> {
                    // TODO : Navigate 추가
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.padding(top = 110.dp),
            painter = painterResource(id = R.drawable.ic_app_logo),
            contentDescription = "app_logo",
        )
        Image(
            painter = painterResource(id = R.drawable.ic_app_name),
            contentDescription = "app_name",
        )
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = "당신의 꿈을 해몽하고 공유해보세요",
            color = Color.White,
        )
        Spacer(modifier = Modifier.weight(1f))

        Image(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .clickable {
                    UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                        if (error != null) {
                            Log.e(TAG, "로그인 실패", error)
                        } else if (token != null) {
                            Log.i(TAG, "로그인 성공 ${token.accessToken}")
                            UserApiClient.instance.me { user, error ->
                                if (error != null) {
                                    Log.e(TAG, "사용자 추가 정보 획득 실패", error)
                                    return@me
                                } else if (user != null) {
                                    viewModel.login(
                                        profileImage = user.kakaoAccount!!.profile?.profileImageUrl,
                                        name = user.kakaoAccount?.profile?.nickname ?: "홍길동",
                                        email = user.kakaoAccount!!.email!!
                                    )
                                }
                            }
                        }
                    }
                },
            painter = painterResource(id = R.drawable.kakao_login_large_wide),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}
