package dev.csse.dtaylo37.triviago.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.csse.dtaylo37.triviago.ui.data.GameCategory
import dev.csse.dtaylo37.triviago.ui.data.Question
import dev.csse.dtaylo37.triviago.ui.data.QuestionType
import dev.csse.dtaylo37.triviago.ui.data.TriviaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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
        triviaRepo.getGameCategories().map {
            GameCategoryScreenUiState(
                gameCategories = it
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5),
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

    fun selectCategory(category: GameCategory) {
        selectedCategory = category
        viewModelScope.launch {
            triviaRepo.getQuestions(category.id).collect {
                questions = it
            }
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
        // Basic check for Multiple Choice, adjust for other types as needed
        lastCorrect = (selectedIndex != null && q.answerOptions.getOrNull(selectedIndex!!) == q.correctAnswer)
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
        }
    }

}
