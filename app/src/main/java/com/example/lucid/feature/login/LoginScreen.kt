@file:Suppress("UNUSED_EXPRESSION")

package com.example.lucid.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lucid.R

@Composable
fun LoginScreen(
    navController: NavController
){
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
        Button(
            modifier = Modifier
                .padding(
                    horizontal = 24.dp,
                    vertical = 16.dp,
                ),
            onClick = { navController.navigate("home") },
        ) {
            Text(text = "카카오 로그인")
        }

    }
}