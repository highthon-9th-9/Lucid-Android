package com.example.lucid.feature.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.lucid.MainActivity
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
import com.kakao.sdk.user.UserApiClient
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

    var flag by remember { mutableStateOf(false) }
    var trigger by remember { mutableStateOf(false) }

    var columnHeightPx by remember {
        mutableStateOf(0f)
    }

    val event = viewModel.event

    val context = LocalContext.current

    var profileImage by remember { mutableStateOf("") }

    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.progress
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )


    LaunchedEffect(Unit) {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "사용자 추가 정보 획득 실패", error)
                return@me
            } else if (user != null) {
                profileImage = user.kakaoAccount!!.profile!!.profileImageUrl!!
            }
        }
    }

    val recognitionListener: RecognitionListener = object : RecognitionListener {
        // 말하기 시작할 준비가되면 호출
        override fun onReadyForSpeech(params: Bundle) {
        }

        // 말하기 시작했을 때 호출
        override fun onBeginningOfSpeech() {
        }

        // 입력받는 소리의 크기를 알려줌
        override fun onRmsChanged(rmsdB: Float) {}

        // 말을 시작하고 인식이 된 단어를 buffer에 담음
        override fun onBufferReceived(buffer: ByteArray) {}

        // 말하기를 중지하면 호출
        override fun onEndOfSpeech() {
        }

        // 오류 발생했을 때 호출
        override fun onError(error: Int) {
            val message = when (error) {
                SpeechRecognizer.ERROR_AUDIO -> "오디오 에러"
                SpeechRecognizer.ERROR_CLIENT -> "클라이언트 에러"
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "퍼미션 없음"
                SpeechRecognizer.ERROR_NETWORK -> "네트워크 에러"
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "네트웍 타임아웃"
                SpeechRecognizer.ERROR_NO_MATCH -> "찾을 수 없음"
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RECOGNIZER 가 바쁨"
                SpeechRecognizer.ERROR_SERVER -> "서버가 이상함"
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "말하는 시간초과"
                else -> "알 수 없는 오류임"
            }
        }

        // 인식 결과가 준비되면 호출
        override fun onResults(results: Bundle) {
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줌
            val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            for (i in matches!!.indices) text = matches[i]
        }

        // 부분 인식 결과를 사용할 수 있을 때 호출
        override fun onPartialResults(partialResults: Bundle) {}

        // 향후 이벤트를 추가하기 위해 예약
        override fun onEvent(eventType: Int, params: Bundle) {}
    }

    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "")    // 여분의 키
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")         // 언어 설정

    val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    speechRecognizer.setRecognitionListener(recognitionListener)    // 리스너 설정

    var showLoading by remember { mutableStateOf(false) }

    fun requestPermission() {
        // 버전 체크, 권한 허용했는지 체크
        if (Build.VERSION.SDK_INT >= 23 &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as MainActivity,
                arrayOf(Manifest.permission.RECORD_AUDIO), 0
            )
        }
    }

    LaunchedEffect(Unit) {
        requestPermission()
    }

    LaunchedEffect(event) {
        event.collect {
            when (it) {
                is HomeViewModel.Event.NavigateToResult -> {
                    showLoading = false
                    val encodedUrl = URLEncoder.encode(it.image, StandardCharsets.UTF_8.toString())
                    navController.navigate("result/${it.data}/$encodedUrl/$text")
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
                    text = "소셜",
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
                    model = profileImage,
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
                            model = profileImage,
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
                            if (trigger) {
                                speechRecognizer.startListening(intent)
                            } else {
                                if (text.isBlank().not()) {
                                    if (showLoading.not()) {
                                        showLoading = true
                                        viewModel.getDream(text)
                                    }
                                }
                            }
                        }
                        .onGloballyPositioned {
                            if (flag.not()) {
                                columnHeightPx = it.boundsInWindow().bottom
                                flag = true
                            }
                            if (flag) {
                                trigger = columnHeightPx > it.boundsInWindow().bottom
                            }
                            Log.d("TEST", "$columnHeightPx / ${it.boundsInWindow().bottom}")
                        }
                        .background(mainPurple, RoundedCornerShape(100))
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.height(20.dp),
                        painter = painterResource(id = if (trigger) R.drawable.ic_mic else R.drawable.ic_moon),
                        contentDescription = null,
                        tint = white
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = if (trigger) "음성입력" else "해몽하기",
                        style = Typography.bodyMedium,
                        color = white
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        if (showLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backGround.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                LottieAnimation(
                    modifier = Modifier.size(150.dp),
                    composition = preloaderLottieComposition,
                    progress = { preloaderProgress },
                    contentScale = ContentScale.FillHeight
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ViewPager() {
    val tabs = listOf("커뮤니티", "아티클")
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
