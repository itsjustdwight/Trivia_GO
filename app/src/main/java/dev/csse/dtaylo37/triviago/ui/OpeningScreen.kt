package dev.csse.dtaylo37.triviago.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.csse.dtaylo37.triviago.ui.assets.FigmaAssets

@Composable
fun OpeningScreen(onContinue: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "tapPulse")

    val tapAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900),
            repeatMode = RepeatMode.Reverse
        ),
        label = "tapAlpha"
    )

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Trivia GO!",
                fontSize = 64.sp,
                color = Color.Black,
            )

            Text(
                text = "Tap anywhere to play",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .alpha(tapAlpha)
            )
        }

        Text(
            text = "© Sanaia Pierre and Dwight Taylor Jr",
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 42.dp)
        )
    }
}