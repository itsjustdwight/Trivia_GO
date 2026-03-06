package dev.csse.dtaylo37.triviago.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.csse.dtaylo37.triviago.ui.assets.FigmaAssets

@Composable
fun OpeningScreen(onContinue: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1E6D7))
            .clickable { onContinue() }
    ) {
        AsyncImage(
            model = FigmaAssets.OpeningBg,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.13f
        )

        Text(
            text = "Trivia GO!",
            fontSize = 64.sp,
            color = Color.Black,
            modifier = Modifier.padding(start = 43.dp, top = 217.dp)
        )

        AsyncImage(
            model = FigmaAssets.LogoColor,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 279.dp)
                .size(412.dp)
                .align(Alignment.TopStart)
        )

        Text(
            text = "Created By: Sanaia Pierre and Dwight Taylor Jr.",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 42.dp)
        )
    }
}