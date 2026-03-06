package dev.csse.dtaylo37.triviago.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TriviaGoViewModel : ViewModel() {

    val gameCategoryList = mutableListOf<String>(
        "History", "Geography", "Science & Math",
        "Pop Culture", "Sports & Games", "Literature", "Mixed Knowledge"
    )

    enum class QuestionType { MC, FILL, MATCH, REARRANGE }

    data class TriviaQuestion(
        val type: QuestionType,
        val prompt: String,
        val option: List<String>,
        val correctIndex: Int = 0
    )

    var selectedCategory by mutableStateOf("")
        private set

    var questions by mutableStateOf<List<TriviaQuestion>>(emptyList())
        private set

    var questionIndex by mutableIntStateOf(0)
        private set

    var selectedIndex by mutableStateOf<Int?>(null)
        private set

    var lastCorrect by mutableStateOf<Boolean?>(null)
        private set

    fun selectCategory(category: String) {
        selectedCategory = category
    }

    fun startGame() {
        questions = listOf(
            TriviaQuestion(
                QuestionType.MC,
                "Sample Multiple Choice?",
                listOf("A", "B", "C", "D"),
                1
            ),
            TriviaQuestion(
                QuestionType.FILL,
                "Fill in the blank:___",
                listOf("Trivia", "Food", "Sleep", "Math"),
                0
            ),
            TriviaQuestion(
                QuestionType.MATCH,
                "Matching demo:",
                listOf("A-1", "B-2", "C-3", "D-4"),
                2
            ),
            TriviaQuestion(
                QuestionType.REARRANGE,
                "Rearrange demo:",
                listOf("First", "Second", "Third", "Fourth"),
                0
            )
        )
        questionIndex = 0
        selectedIndex = null
        lastCorrect = null
    }

    fun selectOption(idx: Int) {
        selectedIndex = idx
    }

    fun submitAnswer() {
        val q = questions[questionIndex]
        lastCorrect = (selectedIndex == q.correctIndex)
    }

    fun nextQuestion(): Boolean {
        val next = questionIndex + 1
        val done = next >= questions.size
        if (!done) {
            questionIndex = next
            selectedIndex = null
            lastCorrect = null
        }
        return done
    }

    fun reset() {
        selectedCategory = ""
        questions = emptyList()
        questionIndex = 0
        selectedIndex = null
        lastCorrect = null
    }

    fun routeForCurrentQuestion(): String {
        val q = questions.getOrNull(questionIndex) ?: return Route.Home.path
        return when (q.type) {
            QuestionType.MC -> Route.MultipleChoice.path
            QuestionType.FILL -> Route.FillBlank.path
            QuestionType.MATCH -> Route.Matching.path
            QuestionType.REARRANGE -> Route.Rearrange.path
        }
    }

}
