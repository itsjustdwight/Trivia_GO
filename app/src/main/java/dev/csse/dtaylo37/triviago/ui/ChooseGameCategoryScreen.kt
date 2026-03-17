package dev.csse.dtaylo37.triviago.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.SportsFootball
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.csse.dtaylo37.triviago.R
import dev.csse.dtaylo37.triviago.ui.assets.FigmaAssets
import dev.csse.dtaylo37.triviago.ui.components.GameScreenFrame
import dev.csse.dtaylo37.triviago.ui.theme.TriviaBlue
import dev.csse.dtaylo37.triviago.ui.theme.TriviaGreen
import dev.csse.dtaylo37.triviago.ui.theme.TriviaPurple
import dev.csse.dtaylo37.triviago.ui.theme.TriviaRed
import dev.csse.dtaylo37.triviago.ui.theme.TriviaTeal
import dev.csse.dtaylo37.triviago.ui.theme.TriviaYellow

private val HeaderBrown = Color(0xFF5B4D4D)

// tile colors from Figma
private val CatHistory = TriviaRed
private val CatGeography = TriviaPurple
private val CatScienceMath = TriviaTeal
private val CatPopCulture = TriviaBlue
private val CatSportsGames = TriviaYellow
private val CatLiterature = TriviaGreen

@Composable
fun ChooseGameCategoryScreen(
    onCategorySelected: (String) -> Unit,
    onProfileSettings: () -> Unit
) {
    GameScreenFrame(
        headerContent = {
            Text(
                text = "Welcome!",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .paint(
                    painter = painterResource(id = R.drawable.trivia_game_background),
                    contentScale = ContentScale.Crop
                )
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(HeaderBrown)
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Choose a Category to Start:",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        CategoryTile(
                            color = CatHistory,
                            icon = Icons.Filled.Timeline,
                            label = "History",
                            onClick = { onCategorySelected("History") },
                            modifier = Modifier.weight(1f)
                        )
                        CategoryTile(
                            color = CatGeography,
                            icon = Icons.Filled.Language,
                            label = "Geography",
                            onClick = { onCategorySelected("Geography") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        CategoryTile(
                            color = CatScienceMath,
                            icon = Icons.Filled.Calculate,
                            label = "Science & Math",
                            onClick = { onCategorySelected("Science & Math") },
                            modifier = Modifier.weight(1f)
                        )
                        CategoryTile(
                            color = CatPopCulture,
                            icon = Icons.Filled.Movie,
                            label = "Pop Culture",
                            onClick = { onCategorySelected("Pop Culture") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        CategoryTile(
                            color = CatSportsGames,
                            icon = Icons.Filled.SportsFootball,
                            label = "Sports & Games",
                            onClick = { onCategorySelected("Sports & Games") },
                            modifier = Modifier.weight(1f)
                        )
                        CategoryTile(
                            color = CatLiterature,
                            icon = Icons.Filled.AutoStories,
                            label = "Literature",
                            onClick = { onCategorySelected("Literature") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    WideTile(label = "Mixed Knowledge", color = HeaderBrown) {
                        onCategorySelected("Mixed Knowledge")
                    }
                }
            }
        }

        Spacer(Modifier.height(18.dp))

        AsyncImage(
            model = FigmaAssets.LogoSmall,
            contentDescription = "TriviaGo logo",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(178.dp)
        )

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(HeaderBrown)
                .clickable { onProfileSettings() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Profile Settings",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun CategoryTile(
    color: Color,
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.White,
                modifier = Modifier.size(52.dp)
            )

            Text(
                text = label,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun WideTile(
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = label,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}