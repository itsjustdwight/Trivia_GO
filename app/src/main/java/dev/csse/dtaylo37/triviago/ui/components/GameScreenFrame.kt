package dev.csse.dtaylo37.triviago.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.csse.dtaylo37.triviago.ui.assets.FigmaAssets

private val HeaderBrown = Color(0xFF5B4D4D)

@Composable
fun GameScreenFrame(
    headerContent: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier.fillMaxSize().background(Color.White)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(HeaderBrown)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 22.dp),
                content = headerContent
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-100).dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            )

            AsyncImage(
                model = FigmaAssets.GameScreensBg,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 110.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
                content = content
            )
        }
    }
}