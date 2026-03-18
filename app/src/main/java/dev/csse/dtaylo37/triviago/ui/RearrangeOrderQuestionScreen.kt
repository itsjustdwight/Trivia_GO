package dev.csse.dtaylo37.triviago.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
fun RearrangeOrderQuestionScreen(
    viewModel: TriviaGoViewModel,
    onSubmit: () -> Unit,
    onQuitHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentQuestion = viewModel.questions.getOrNull(viewModel.questionIndex)
    
    // We start with the options shuffled
    val initialOrder = remember(currentQuestion) {
        currentQuestion?.answerOptions?.shuffled() ?: emptyList()
    }
    val currentOrder = remember(initialOrder) { 
        mutableStateListOf<String>().apply { addAll(initialOrder) } 
    }

    val showResult = viewModel.lastCorrect

    REARRANGEQuestionScreen(
        categoryName = viewModel.selectedCategory?.categoryName ?: "Category Name",
        questionText = currentQuestion?.text ?: "Loading Questions...",
        answerOptions = currentOrder,
        showResult = showResult,
        onReorder = { from, to ->
            if (from != to && showResult == null) {
                val item = currentOrder.removeAt(from)
                currentOrder.add(to, item)
            }
        },
        onSubmit = {
            if (showResult == null) {
                val isCorrect = currentOrder.toList().joinToString(",") == currentQuestion?.correctAnswer
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
fun REARRANGEQuestionScreen(
    categoryName: String,
    questionText: String,
    answerOptions: List<String>,
    showResult: Boolean?,
    onReorder: (Int, Int) -> Unit,
    onSubmit: () -> Unit,
    onQuitHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val headerImage = remember(categoryName) { ImageForCategory(categoryName) }

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

            Text(
                text = questionText,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            val colors = listOf(TriviaRed, TriviaPurple, TriviaTeal, TriviaGreen, TriviaYellow)
            
            RearrangeReorderableColumn(
                items = answerOptions,
                onReorder = onReorder,
                colors = colors,
                modifier = Modifier.fillMaxWidth(),
                enabled = showResult == null
            )

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
private fun RearrangeReorderableColumn(
    items: List<String>,
    onReorder: (Int, Int) -> Unit,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val density = LocalDensity.current
    val itemHeightPx = with(density) { 40.dp.toPx() }
    val spacingPx = with(density) { 16.dp.toPx() }
    val totalStep = itemHeightPx + spacingPx

    var draggingIndex by remember { mutableStateOf<Int?>(null) }
    var dragOffsetY by remember { mutableStateOf(0f) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
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
                    answerText = item
                )
            }
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
            modifier = Modifier.fillMaxSize(),
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

private fun ImageForCategory(categoryName: String): Int {
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
fun RearrangeOrderQuestionScreenPreview() {
    REARRANGEQuestionScreen(
        categoryName = "Pop Culture",
        questionText = "Highest grossing movie franchises of all time",
        answerOptions = listOf("Star Wars", "Harry Potter", "Lord of the Rings", "The Matrix", "The MCU"),
        showResult = null,
        onReorder = { _, _ -> },
        onSubmit = {},
        onQuitHome = {}
    )
}
