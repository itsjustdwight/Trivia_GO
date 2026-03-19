package dev.csse.dtaylo37.triviago.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import dev.csse.dtaylo37.triviago.R
import dev.csse.dtaylo37.triviago.ui.components.GameScreenFrame
import dev.csse.dtaylo37.triviago.ui.components.ResultBanner
import dev.csse.dtaylo37.triviago.ui.theme.*
import kotlin.math.roundToInt

@Composable
fun MatchingQuestionScreen(
    viewModel: TriviaGoViewModel,
    onSubmit: () -> Unit,
    onQuitHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentQuestion = viewModel.questions.getOrNull(viewModel.questionIndex)

    val leftItems = remember(currentQuestion) {
        currentQuestion?.answerOptions?.filterIndexed { i, _ -> i % 2 == 0 } ?: emptyList()
    }
    val correctRightOrder = remember(currentQuestion) {
        currentQuestion?.correctAnswer?.split(",") ?: emptyList()
    }

    val currentRightItems = remember(correctRightOrder) {
        var shuffled = correctRightOrder.shuffled()
        while (shuffled.size > 1 && shuffled == correctRightOrder) {
            shuffled = correctRightOrder.shuffled()
        }
        mutableStateListOf<String>().apply { addAll(shuffled) }
    }

    val showResult = viewModel.lastCorrect

    MATCHQuestionScreen(
        categoryName = viewModel.selectedCategory?.categoryName ?: "Science & Math",
        questionText = currentQuestion?.text ?: "Match the elements with their names:",
        leftItems = leftItems,
        rightItems = currentRightItems,
        showResult = showResult,
        timeLeft = viewModel.timeLeft,
        totalTime = viewModel.totalTime,
        onReorder = { from, to ->
            if (from != to && showResult == null) {
                val item = currentRightItems.removeAt(from)
                currentRightItems.add(to, item)
            }
        },
        onSubmit = {
            if (showResult == null) {
                val isCorrect = currentRightItems.toList() == correctRightOrder
                viewModel.submitManualResult(isCorrect)
            } else {
                onSubmit()
            }
        },
        onQuitHome = onQuitHome,
        modifier = modifier
    )
}

@Composable
fun MATCHQuestionScreen(
    categoryName: String,
    questionText: String,
    leftItems: List<String>,
    rightItems: List<String>,
    showResult: Boolean?,
    timeLeft: Int,
    totalTime: Int,
    onReorder: (Int, Int) -> Unit,
    onSubmit: () -> Unit,
    onQuitHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val headerImage = remember(categoryName) { imageForCategory(categoryName) }
    val progress = timeLeft.toFloat() / totalTime

    GameScreenFrame(
        modifier = modifier,
        headerContent = {
            Spacer(Modifier.width(8.dp))
            Text(
                text = categoryName,
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.width(8.dp))
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

            val colors = listOf(TriviaRed, TriviaPurple, TriviaTeal, TriviaGreen, TriviaYellow)
            
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.5f).padding(end = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    leftItems.forEachIndexed { index, item ->
                        AnswerTile(
                            color = colors[index % colors.size],
                            answerText = item,
                            isLeft = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                MatchReorderableColumn(
                    items = rightItems,
                    onReorder = onReorder,
                    colors = colors,
                    enabled = showResult == null,
                    modifier = Modifier.align(Alignment.TopEnd).fillMaxWidth(0.5f).padding(start = 4.dp)
                )
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
private fun MatchReorderableColumn(
    items: List<String>,
    onReorder: (Int, Int) -> Unit,
    colors: List<Color>,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val itemHeightPx = with(density) { 56.dp.toPx() }
    val spacingPx = with(density) { 12.dp.toPx() }
    val totalStep = itemHeightPx + spacingPx

    // Keep track of which index is being dragged and its current offset
    var draggingIndex by remember { mutableStateOf<Int?>(null) }
    var dragOffsetY by remember { mutableStateOf(0f) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.forEachIndexed { index, item ->
            val isDragging = draggingIndex == index
            
            val verticalOffset = if (isDragging) dragOffsetY else 0f

            Box(
                modifier = Modifier
                    .zIndex(if (isDragging) 1f else 0f)
                    .offset { IntOffset(0, verticalOffset.roundToInt()) }
                    .then(if (enabled) {
                        Modifier.pointerInput(index) {
                            detectDragGestures(
                                onDragStart = { 
                                    draggingIndex = index
                                    dragOffsetY = 0f
                                },
                                onDragEnd = {
                                    val targetIndex = (index + (dragOffsetY / totalStep).roundToInt())
                                        .coerceIn(0, items.size - 1)
                                    onReorder(index, targetIndex)
                                    draggingIndex = null
                                    dragOffsetY = 0f
                                },
                                onDragCancel = {
                                    draggingIndex = null
                                    dragOffsetY = 0f
                                },
                                onDrag = { change, dragAmount ->
                                    dragOffsetY += dragAmount.y
                                    change.consume()
                                }
                            )
                        }
                    } else Modifier)
            ) {
                AnswerTile(
                    color = colors[index % colors.size],
                    answerText = item,
                    isLeft = false,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun AnswerTile(
    color: Color,
    answerText: String,
    isLeft: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.height(56.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = answerText,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier
                .size(24.dp)
                .align(if (isLeft) Alignment.CenterEnd else Alignment.CenterStart)
                .offset(x = if (isLeft) 6.dp else (-6).dp)
                .background(color, CircleShape)
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
fun MatchingQuestionScreenPreview() {
    MATCHQuestionScreen(
        categoryName = "Science & Math",
        questionText = "Match the following elements:",
        leftItems = listOf("Fe", "Au", "Ag", "Pb", "Cu"),
        rightItems = listOf("Iron", "Gold", "Silver", "Lead", "Copper"),
        showResult = null,
        timeLeft = 5,
        totalTime = 10,
        onReorder = { _, _ -> },
        onSubmit = {},
        onQuitHome = {}
    )
}
