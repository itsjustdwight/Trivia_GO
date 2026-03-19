package dev.csse.dtaylo37.triviago.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.csse.dtaylo37.triviago.R
import dev.csse.dtaylo37.triviago.ui.components.GameScreenFrame
import dev.csse.dtaylo37.triviago.ui.components.PrimaryButton
import dev.csse.dtaylo37.triviago.ui.theme.TriviaGreen
import dev.csse.dtaylo37.triviago.ui.theme.TriviaRed
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
        isCorrect = viewModel.lastCorrect ?: true,
        score = viewModel.score,
        totalQuestions = viewModel.totalQuestions,
        isFinished = viewModel.isLastQuestion,
        onNext = onNext,
        onHome = onHome,
        modifier = modifier
    )
}

@Composable
fun TransitionScreen(
    categoryName: String,
    isCorrect: Boolean,
    score: Int,
    totalQuestions: Int,
    isFinished: Boolean,
    onNext: () -> Unit,
    onHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val headerImage = remember(categoryName) { imageForCategory(categoryName) }

    GameScreenFrame(
        modifier = modifier,
        headerContent = {
            Text(
                text = categoryName,
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(4.dp))

            Image(
                painter = painterResource(id = headerImage),
                contentDescription = "Subject Graphic",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp)
                    .clip(RoundedCornerShape(20.dp))
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(TriviaRed, TriviaYellow, TriviaGreen)
                        )
                    )
            )

            if (isFinished) {
                Text(
                    text = "Final Score:",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$score / $totalQuestions",
                    color = Color.Black,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            } else {
                Text(
                    text = "Time Left:",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(Modifier.height(if (isFinished) 10.dp else 40.dp))

            Text(
                text = if (isFinished) {
                    if (score == totalQuestions) {
                        "Perfect! You're a trivia master!"
                    } else if (score >= totalQuestions / 2) {
                        "Great job! High score!"
                    } else {
                        "Good effort! Practice makes perfect."
                    }
                } else {
                    if (isCorrect) {
                        "Great job! You're on fire!\nKeep the momentum going."
                    } else {
                        "Don't give up!\nYou'll get the next one."
                    }
                },
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.weight(1f))

            PrimaryButton(
                text = if (isFinished) "Finish" else "Next Question",
                onClick = onNext
            )

            Text(
                text = "Back to home",
                color = Color.Black,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .clickable { onHome() }
            )
        }
    }
}

private fun imageForCategory(categoryName: String): Int {
    return when (categoryName) {
        "History" -> R.drawable.history_graphic
        "Geography" -> R.drawable.geography_graphic
        "Science & Math" -> R.drawable.sciencemath_graphic
        "Pop Culture" -> R.drawable.popculture_graphic
        "Sports & Games" -> R.drawable.sportsgames_graphic
        "Literature" -> R.drawable.literature_graphic
        "Mixed Knowledge" -> R.drawable.mixedknowledge_graphic
        else -> R.drawable.history_graphic
    }
}

@Preview(showBackground = true)
@Composable
fun NextQuestionTransitionScreenPreview() {
    TransitionScreen(
        categoryName = "History",
        isCorrect = true,
        score = 3,
        totalQuestions = 5,
        isFinished = false,
        onNext = {},
        onHome = {}
    )
}

@Preview(showBackground = true)
@Composable
fun FinalScoreTransitionScreenPreview() {
    TransitionScreen(
        categoryName = "History",
        isCorrect = true,
        score = 4,
        totalQuestions = 5,
        isFinished = true,
        onNext = {},
        onHome = {}
    )
}
