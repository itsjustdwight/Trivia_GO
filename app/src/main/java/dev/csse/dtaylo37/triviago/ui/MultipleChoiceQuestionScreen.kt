package dev.csse.dtaylo37.triviago.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.csse.dtaylo37.triviago.R
import dev.csse.dtaylo37.triviago.ui.components.GameScreenFrame
import dev.csse.dtaylo37.triviago.ui.data.TriviaRepository
import dev.csse.dtaylo37.triviago.ui.theme.TriviaGreen
import dev.csse.dtaylo37.triviago.ui.theme.TriviaPurple
import dev.csse.dtaylo37.triviago.ui.theme.TriviaRed
import dev.csse.dtaylo37.triviago.ui.theme.TriviaTeal
import dev.csse.dtaylo37.triviago.ui.theme.TriviaYellow

@Composable
fun MultipleChoiceQuestionScreen(
    viewModel: TriviaGoViewModel,
    onSubmit: () -> Unit,
    onQuitHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentQuestion = viewModel.questions.getOrNull(viewModel.questionIndex)

    MCQuestionScreen(
        categoryName = viewModel.selectedCategory?.categoryName ?: "Category Name",
        questionText = currentQuestion?.text ?: "Loading Questions...",
        answerOptions = currentQuestion?.answerOptions ?: emptyList(),
        selectedOption = {
            viewModel.selectOption(it)
        },
        modifier = modifier
    )
}

@Composable
fun MCQuestionScreen(
    categoryName: String,
    questionText: String,
    answerOptions: List<String>,
    selectedOption: (Int) -> Unit,
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
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.history_graphic),
                contentDescription = "History Graphic",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp)
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

            // Question
            Text(
                text = questionText,
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            // Answer Choices
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                val colors = listOf(TriviaRed, TriviaPurple, TriviaTeal, TriviaGreen)
                answerOptions.forEachIndexed { index, option ->
                    AnswerTile(
                        color = colors.getOrElse(index) { Color.Gray },
                        answerText = option,
                        onClick = { selectedOption(index) }
                    )
                }

            }
        }
    }
}

@Composable
private fun AnswerTile(
    color: Color,
    answerText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = answerText,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MultipleChoiceQuestionScreenPreview() {
    MCQuestionScreen(
        categoryName = "History",
        questionText = "Who was the first president of the United States?",
        answerOptions = listOf("George Washington", "Thomas Jefferson", "Abraham Lincoln", "Benjamin Franklin"),
        selectedOption = {}
    )
}