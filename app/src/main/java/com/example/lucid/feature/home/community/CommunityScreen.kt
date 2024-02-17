package com.example.lucid.feature.home.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lucid.R
import com.example.lucid.ui.theme.Typography
import com.example.lucid.ui.theme.backGround
import com.example.lucid.ui.theme.darkWhite

@Composable
fun CommunityScreen(
    paddingValues: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
            .background(backGround),
    ) {
        item(4) {
            CommunityItem()
        }
    }

}

@Composable
fun CommunityItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 15.dp,
            )
            .background(backGround),
    ) {
        Image(
            modifier = Modifier.size(32.dp),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "profile",
        )
        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = "김정윤",
                style = Typography.titleSmall,
                color = darkWhite,
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = "sdasdasdasdasdasdasdasdad",
                style = Typography.bodySmall,
                color = darkWhite,
            )
            Image(
                modifier = Modifier.padding(top = 8.dp),
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "content",
            )
        }
    }
}
