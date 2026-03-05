package dev.csse.dtaylo37.triviago.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.R
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.csse.dtaylo37.triviago.GameCategory
import dev.csse.dtaylo37.triviago.ui.theme.BackgroundPurple
import dev.csse.dtaylo37.triviago.ui.theme.BackgroundTan

@Preview
@Composable
fun ChooseGameCategoryScreen() {
    var selectedGameCategory by remember { mutableStateOf<GameCategory?>(null) }
    val screenTitleText = selectedGameCategory?.displayName ?: "Welcome Player"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundPurple)
            .padding(top = 10.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 75.dp),
            shape = RoundedCornerShape(
                topStart = 25.dp,
                topEnd = 25.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            color = BackgroundTan
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text("To Start, Choose a Category: ")

                Spacer(modifier = Modifier.height(16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(GameCategory.entries) { category ->
                        GameCategoryCard(
                            value = category
                        )
                    }
                }
            }
        }
        TriviaGoAppBar(title = screenTitleText)
    }
}

@Composable
fun GameCategoryCard(
    value: GameCategory,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(0.75f)
            .background(
                color = value.backgroundColor,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = value.iconRes),
            contentDescription = value.displayName,
            modifier = Modifier.fillMaxSize(0.6f),
            contentScale = ContentScale.Fit
        )
    }
}
