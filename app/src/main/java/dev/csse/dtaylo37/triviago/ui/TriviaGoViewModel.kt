package dev.csse.dtaylo37.triviago.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.csse.dtaylo37.triviago.ui.data.GameCategory
import dev.csse.dtaylo37.triviago.ui.data.Question
import dev.csse.dtaylo37.triviago.ui.data.QuestionType
import dev.csse.dtaylo37.triviago.ui.data.TriviaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class GameCategoryScreenUiState(
    val gameCategories: List<GameCategory> = emptyList(),
    val questionList: List<Question> = emptyList(),
    val selectedGameCategory: Set<GameCategory> = emptySet()
)

class TriviaGoViewModel(
    private val triviaRepo: TriviaRepository
) : ViewModel() {

    val uiState: StateFlow<GameCategoryScreenUiState> =
        triviaRepo.getGameCategories()
            .map {
                GameCategoryScreenUiState(
                    gameCategories = it
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = GameCategoryScreenUiState()
            )

    var selectedCategory by mutableStateOf<GameCategory?>(null)
        private set

    var questions by mutableStateOf<List<Question>>(emptyList())
        private set

    var questionIndex by mutableIntStateOf(0)
        private set

    var selectedIndex by mutableStateOf<Int?>(null)
        private set

    var lastCorrect by mutableStateOf<Boolean?>(null)
        private set

    fun selectCategory(categoryName: String) {
        viewModelScope.launch {
            val category = triviaRepo.getGameCategories()
                .map { list -> list.find { it.categoryName == categoryName } }
                .firstOrNull()

            selectedCategory = category

            if (category != null) {
                questions = triviaRepo.getQuestions(category.id).firstOrNull().orEmpty()
            } else {
                questions = emptyList()
            }

            questionIndex = 0
            selectedIndex = null
            lastCorrect = null
        }
    }

    fun startGame() {
        questionIndex = 0
        selectedIndex = null
        lastCorrect = null
    }

    fun selectOption(idx: Int) {
        selectedIndex = idx
    }

    fun submitAnswer() {
        val q = questions.getOrNull(questionIndex) ?: return
        val chosenAnswer = selectedIndex?.let { q.answerOptions.getOrNull(it) }

        lastCorrect = chosenAnswer == q.correctAnswer
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
        selectedCategory = null
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
            QuestionType.TF -> Route.TrueFalse.path
        }
    }
}

class TriviaGoViewModelFactory(
    private val triviaRepo: TriviaRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TriviaGoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TriviaGoViewModel(triviaRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}