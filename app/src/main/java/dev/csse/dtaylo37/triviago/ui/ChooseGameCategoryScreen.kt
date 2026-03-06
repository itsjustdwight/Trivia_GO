package dev.csse.dtaylo37.triviago.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
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

@Preview
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
        Text(
            text = "Choose a Category to Start:",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 8.dp, bottom = 18.dp),
            color = Color.Black
        )

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                CategoryTile(
                    color = CatHistory,
                    iconUrl = FigmaAssets.IconHistory,
                    onClick = { onCategorySelected("History") },
                    modifier = Modifier.weight(1f)
                )
                CategoryTile(
                    color = CatGeography,
                    iconUrl = FigmaAssets.IconGeography,
                    onClick = { onCategorySelected("Geography") },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                CategoryTile(
                    color = CatScienceMath,
                    iconUrl = FigmaAssets.IconMath,
                    onClick = { onCategorySelected("Science & Math") },
                    modifier = Modifier.weight(1f)
                )
                CategoryTile(
                    color = CatPopCulture,
                    iconUrl = FigmaAssets.IconMusic,
                    onClick = { onCategorySelected("Pop Culture") },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                CategoryTile(
                    color = CatSportsGames,
                    iconUrl = FigmaAssets.IconSports,
                    onClick = { onCategorySelected("Sports & Games") },
                    modifier = Modifier.weight(1f)
                )
                CategoryTile(
                    color = CatLiterature,
                    iconUrl = FigmaAssets.IconLiterature,
                    onClick = { onCategorySelected("Literature") },
                    modifier = Modifier.weight(1f)
                )
            }

            WideTile(label = "Mixed Knowledge", color = HeaderBrown) {
                onCategorySelected("Mixed Knowledge")
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
    iconUrl: String,
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
        AsyncImage(
            model = iconUrl,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
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
        Text(
            text = label,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}