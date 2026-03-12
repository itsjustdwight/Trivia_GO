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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import dev.csse.dtaylo37.triviago.ui.theme.BackgroundPurple
import dev.csse.dtaylo37.triviago.ui.theme.TriviaBlue
import dev.csse.dtaylo37.triviago.ui.theme.TriviaGreen
import dev.csse.dtaylo37.triviago.ui.theme.TriviaPurple
import dev.csse.dtaylo37.triviago.ui.theme.TriviaRed
import dev.csse.dtaylo37.triviago.ui.theme.TriviaTeal
import dev.csse.dtaylo37.triviago.ui.theme.TriviaYellow

@Composable
fun RearrangeOrderQuestionScreen(
    viewModel: TriviaGoViewModel,
    onSubmit: () -> Unit,
    onQuitHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentQuestion = viewModel.questions.getOrNull(viewModel.questionIndex)

    REARRANGEQuestionScreen(
        categoryName = viewModel.selectedCategory?.categoryName ?: "Category Name",
        questionText = currentQuestion?.text ?: "Loading Questions...",
        answerOptions = currentQuestion?.answerOptions ?: emptyList(),
        selectedOption = {
            viewModel.selectOption(it)
        },
        modifier = modifier,
        onSubmit = {
            viewModel.submitAnswer()
//            onQuitHome()
        }
    )
}

@Composable
fun REARRANGEQuestionScreen(
    categoryName: String,
    questionText: String,
    answerOptions: List<String>,
    selectedOption: (Int) -> Unit,
    onSubmit: () -> Unit,
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


            // Question
            Text(
                text = questionText,
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            // Answer Choices
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                val colors = listOf(TriviaRed, TriviaPurple, TriviaTeal, TriviaGreen, TriviaYellow)

                answerOptions.forEachIndexed { index, option ->
                    AnswerTile(
                        color = colors.getOrElse(index) { Color.Gray },
                        answerText = option
                    )
                }
            }

            // Submit Button
            PrimaryButton(
                text = "Submit",
                onClick = onSubmit
            )
        }
    }
}

@Composable
private fun AnswerTile(
    color: Color,
    answerText: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(24.dp))

            Text(
                text = answerText,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Drag Handle",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RearrangeOrderQuestionScreenPreview() {
    REARRANGEQuestionScreen(
        categoryName = "Pop Culture",
        questionText = "Highest grossing movie franchises of all time",
        answerOptions = listOf("Star Wars", "Harry Potter", "Lord of the Rings", "The Matrix", "The MCU"),
        selectedOption = {},
        onSubmit = {}
    )
}