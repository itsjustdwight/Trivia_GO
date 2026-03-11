package dev.csse.dtaylo37.triviago.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.csse.dtaylo37.triviago.ui.assets.FigmaAssets
import dev.csse.dtaylo37.triviago.ui.theme.BackgroundTan

private val HeaderBrown = Color(0xFF5B4D4D)

@Composable
fun GameScreenFrame(
    headerContent: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(HeaderBrown)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.Center,
                content = headerContent
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 85.dp) // Starts 120dp from top, overlapping the 220dp header
                .clip(RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp))
                .background(BackgroundTan)
        ) {
            AsyncImage(
                model = FigmaAssets.GameScreensBg,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                alpha = 0.5f
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                content = content
            )
        }
    }
}
