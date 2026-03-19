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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
        
    var score by mutableIntStateOf(0)
        private set

    var timeLeft by mutableIntStateOf(10)
        private set
    
    val totalTime = 10
    private var timerJob: Job? = null

    suspend fun selectCategory(categoryName: String) {
        val categoryList = triviaRepo.getGameCategories().map { it }.firstOrNull() ?: emptyList()
        val category = categoryList.find { it.categoryName == categoryName }

        selectedCategory = category

        if (category != null) {
            // Get exactly 4 questions or as many as available
            questions = triviaRepo.getQuestions(category.id).map {
                it.shuffled().take(4)
            }.firstOrNull().orEmpty()
        } else {
            questions = emptyList()
        }

        resetGameState()
    }

    private fun resetGameState() {
        questionIndex = 0
        selectedIndex = null
        lastCorrect = null
        score = 0
        startTimer()
    }

    fun startGame() {
        resetGameState()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timeLeft = totalTime
        timerJob = viewModelScope.launch {
            while (timeLeft > 0 && lastCorrect == null) {
                delay(1000)
                timeLeft--
            }
            if (timeLeft == 0 && lastCorrect == null) {
                submitManualResult(false)
            }
        }
    }

    fun selectOption(idx: Int) {
        selectedIndex = idx
    }

    fun submitAnswer() {
        val q = questions.getOrNull(questionIndex) ?: return
        val chosenAnswer = selectedIndex?.let { q.answerOptions.getOrNull(it) }

        val correct = chosenAnswer == q.correctAnswer
        lastCorrect = correct
        if (correct) {
            score++
        }
        timerJob?.cancel()
    }
    
    fun submitManualResult(isCorrect: Boolean) {
        lastCorrect = isCorrect
        if (isCorrect) {
            score++
        }
        timerJob?.cancel()
    }

    fun nextQuestion(): Boolean {
        val next = questionIndex + 1
        val done = next >= questions.size

        if (!done) {
            questionIndex = next
            selectedIndex = null
            lastCorrect = null
            startTimer()
        }

        return done
    }

    fun reset() {
        selectedCategory = null
        questions = emptyList()
        questionIndex = 0
        selectedIndex = null
        lastCorrect = null
        timerJob?.cancel()
    }

    fun routeForCurrentQuestion(): String {
        val q = questions.getOrNull(questionIndex) ?: return Route.Home.path
        return when (q.type) {
            QuestionType.MC -> Route.MultipleChoice.path
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
