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
import dev.csse.dtaylo37.triviago.ui.theme.TriviaRed
import dev.csse.dtaylo37.triviago.ui.theme.TriviaYellow

@Composable
fun MultipleChoiceQuestionScreen(
    viewModel: TriviaGoViewModel,
    onSubmit: () -> Unit,
    onQuitHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    GameScreenFrame(
        headerContent = {
            Text(
                text = "{viewModel.selectedCategory}",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        }
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.history_graphic),
                contentDescription = "History Graphic",
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
                        brush = Brush.horizontalGradient(listOf(TriviaGreen, TriviaYellow, TriviaRed))
                    )
            )
            Text(
                text = "Time Left: ",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            // Question
            Text(
                text = "Question Here",
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            // Answer Choices
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                AnswerTile(
                    color = TriviaRed,
                    answerText = "Answer Here",
                    onClick = { viewModel.selectOption(0) }
                )

                Spacer(Modifier.height(12.dp))

                AnswerTile(
                    color = TriviaRed,
                    answerText = "Answer Here",
                    onClick = { viewModel.selectOption(0) }
                )

                Spacer(Modifier.height(12.dp))

                AnswerTile(
                    color = TriviaRed,
                    answerText = "Answer Here",
                    onClick = { viewModel.selectOption(0) }
                )

                Spacer(Modifier.height(12.dp))

                AnswerTile(
                    color = TriviaRed,
                    answerText = "Answer Here",
                    onClick = { viewModel.selectOption(0) }
                )
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
            .height(100.dp)
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