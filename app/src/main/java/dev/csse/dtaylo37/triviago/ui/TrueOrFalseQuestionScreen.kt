package dev.csse.dtaylo37.triviago.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.csse.dtaylo37.triviago.R
import dev.csse.dtaylo37.triviago.ui.components.GameScreenFrame
import dev.csse.dtaylo37.triviago.ui.components.ResultBanner
import dev.csse.dtaylo37.triviago.ui.theme.TriviaGreen
import dev.csse.dtaylo37.triviago.ui.theme.TriviaPurple
import dev.csse.dtaylo37.triviago.ui.theme.TriviaRed
import dev.csse.dtaylo37.triviago.ui.theme.TriviaYellow

@Composable
fun TrueOrFalseQuestionScreen(
    viewModel: TriviaGoViewModel,
    onSubmit: () -> Unit,
    onQuitHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentQuestion = viewModel.questions.getOrNull(viewModel.questionIndex)

    TFQuestionScreen(
        categoryName = viewModel.selectedCategory?.categoryName ?: "Category Name",
        questionText = currentQuestion?.text ?: "Loading Questions...",
        answerOptions = currentQuestion?.answerOptions ?: listOf("True", "False"),
        selectedIndex = viewModel.selectedIndex,
        showResult = viewModel.lastCorrect,
        timeLeft = viewModel.timeLeft,
        totalTime = viewModel.totalTime,
        onOptionSelected = { if (viewModel.lastCorrect == null) viewModel.selectOption(it) },
        onSubmit = onSubmit,
        onQuitHome = onQuitHome,
        modifier = modifier
    )
}

@Composable
fun TFQuestionScreen(
    categoryName: String,
    questionText: String,
    answerOptions: List<String>,
    selectedIndex: Int?,
    showResult: Boolean?,
    timeLeft: Int,
    totalTime: Int,
    onOptionSelected: (Int) -> Unit,
    onSubmit: () -> Unit,
    onQuitHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val headerImage = remember(categoryName) { imageForCategory(categoryName) }
    val progress = timeLeft.toFloat() / totalTime

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
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = headerImage),
                    contentDescription = "Subject Graphic",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )
                if (showResult != null) {
                    ResultBanner(isCorrect = showResult)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.LightGray.copy(alpha = 0.3f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(TriviaRed, TriviaYellow, TriviaGreen)
                            )
                        )
                )
            }

            Text(
                text = "Time Left: ${timeLeft}s",
                color = if (timeLeft <= 5) TriviaRed else Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = questionText,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                val colors = listOf(TriviaGreen, TriviaRed)

                answerOptions.forEachIndexed { index, option ->
                    AnswerTile(
                        color = colors.getOrElse(index) { Color.Gray },
                        answerText = option,
                        isSelected = selectedIndex == index,
                        onClick = { onOptionSelected(index) }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onQuitHome,
                    modifier = Modifier.weight(1f),
                    border = BorderStroke(2.dp, TriviaPurple)
                ) {
                    Text("Quit")
                }

                Button(
                    onClick = onSubmit,
                    enabled = selectedIndex != null,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = TriviaPurple)
                ) {
                    Text(if (showResult == null) "Submit" else "Continue")
                }
            }
        }
    }
}

@Composable
private fun AnswerTile(
    color: Color,
    answerText: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(300.dp)
            .width(165.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) color.copy(alpha = 0.75f) else color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = answerText,
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
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
fun TrueOrFalseQuestionScreenPreview() {
    TFQuestionScreen(
        categoryName = "Geography",
        questionText = "Earth is flat.",
        answerOptions = listOf("True", "False"),
        selectedIndex = 1,
        showResult = null,
        timeLeft = 5,
        totalTime = 10,
        onOptionSelected = {},
        onSubmit = {},
        onQuitHome = {}
    )
}
