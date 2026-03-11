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
    onQuitHome: () -> Unit
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
                painter = painterResource(id = R.drawable.scienceandmath_graphic),
                contentDescription = "Geography Graphic",
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
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    AnswerTile(
                        color = TriviaRed,
                        answerText = "Answer Here",
                    )

                    Spacer(Modifier.height(12.dp))

                    AnswerTile(
                        color = TriviaPurple,
                        answerText = "Answer Here",
                    )

                    Spacer(Modifier.height(12.dp))

                    AnswerTile(
                        color = TriviaTeal,
                        answerText = "Answer Here",
                    )

                    Spacer(Modifier.height(12.dp))

                    AnswerTile(
                        color = TriviaGreen,
                        answerText = "Answer Here",
                    )

                    Spacer(Modifier.height(12.dp))

                    AnswerTile(
                        color = TriviaBlue,
                        answerText = "Answer Here",
                    )

                    Spacer(Modifier.height(12.dp))

                    AnswerTile(
                        color = TriviaYellow,
                        answerText = "Answer Here",
                    )

                    Spacer(Modifier.height(12.dp))

                    AnswerTile(
                        color = BackgroundPurple,
                        answerText = "Answer Here",
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

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
            .height(60.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color)
            .padding(horizontal = 24.dp),
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
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
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