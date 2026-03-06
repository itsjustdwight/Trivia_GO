package dev.csse.dtaylo37.triviago.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.csse.dtaylo37.triviago.ui.components.GameScreenFrame
import dev.csse.dtaylo37.triviago.ui.components.PrimaryButton
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(
    category: String,
    startSeconds: Int,
    onBack: () -> Unit,
    onDone: () -> Unit
) {
    var seconds by remember { mutableIntStateOf(startSeconds) }

    LaunchedEffect(Unit) {
        while (seconds > 0) {
            delay(1000)
            seconds -= 1
        }
        onDone()
    }

    GameScreenFrame(
        headerContent = {
            Text(
                text = if (category.isBlank()) "Category Name" else category,
                color = Color.White,
                fontSize = 33.sp,
                fontWeight = FontWeight.Bold
            )
        }
    ) {
        Text(
            text = "Loading Questions...",
            fontSize = 36.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 10.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = seconds.coerceAtLeast(1).toString(),
                fontSize = 160.sp,
                color = Color.Black
            )
        }

        PrimaryButton(text = "Go Back", onClick = onBack)
    }
}