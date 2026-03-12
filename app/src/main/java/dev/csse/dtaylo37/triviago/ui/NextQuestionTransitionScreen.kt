package dev.csse.dtaylo37.triviago.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.csse.dtaylo37.triviago.R
import dev.csse.dtaylo37.triviago.ui.components.GameScreenFrame
import dev.csse.dtaylo37.triviago.ui.components.PrimaryButton
import dev.csse.dtaylo37.triviago.ui.theme.TriviaGreen
import dev.csse.dtaylo37.triviago.ui.theme.TriviaPurple
import dev.csse.dtaylo37.triviago.ui.theme.TriviaRed
import dev.csse.dtaylo37.triviago.ui.theme.TriviaTeal
import dev.csse.dtaylo37.triviago.ui.theme.TriviaYellow

@Composable
fun NextQuestionTransitionScreen(
    viewModel: TriviaGoViewModel,
    onNext: () -> Unit,
    onHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    TransitionScreen(
        categoryName = viewModel.selectedCategory?.categoryName ?: "Category Name",
        onNext = onNext,
        onHome = onHome,
        modifier = modifier
    )
}

@Composable
fun TransitionScreen(
    categoryName: String,
    onNext: () -> Unit,
    onHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    GameScreenFrame(
        headerContent = {
            Text(
                text = categoryName,
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        }
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Spacer(Modifier.height(1.dp))

            val headerImages = listOf(
                R.drawable.history_graphic,
                R.drawable.geography_graphic,
                R.drawable.sciencemath_graphic,
                R.drawable.popculture_graphic,
                R.drawable.sportsgames_graphic,
                R.drawable.literature_graphic,
                R.drawable.mixedknowledge_graphic
            )
            Image(
                painter = painterResource(id = headerImages.random()),
                contentDescription = "Subject Graphic",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp))
            )

            // Timer Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        brush = Brush.horizontalGradient(listOf(TriviaRed, TriviaYellow, TriviaGreen))
                    )
            )
            Text(
                text = "Time Left: ",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(12.dp))

            // Congratulatory Message
            Text(
                text = "Message of congratulations\n" +
                        "or encouragement",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(164.dp))

            // Submit Button
            PrimaryButton(
                text = "Next Question",
                onClick = onNext
            )

            // Home Button
            PrimaryButton(
                text = "Back to Home",
                onClick = onHome
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun NextQuestionTransitionScreenPreview() {
    TransitionScreen(
        categoryName = "Category Name",
        onNext = {},
        onHome = {}
    )
}
