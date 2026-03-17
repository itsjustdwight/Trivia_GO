package dev.csse.dtaylo37.triviago.ui.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TriviaRepository(context: Context) {

    private val databaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            CoroutineScope(Dispatchers.IO).launch {
                starterData()
            }
        }
    }

    private val database: TriviaDatabase = Room.databaseBuilder(
        context,
        TriviaDatabase::class.java,
        "trivia.db"
    )
        .fallbackToDestructiveMigration()
        .addCallback(databaseCallback)
        .build()

    private val gameCategoryDao = database.GameCategoryDao()
    private val questionDao = database.QuestionDao()

    fun getGameCategory(gameCategoryId: Long) = gameCategoryDao.getGameCategory(gameCategoryId)
    fun getGameCategories() = gameCategoryDao.getGameCategories()

    fun getQuestion(questionId: Long) = questionDao.getQuestion(questionId)
    fun getQuestions(gameCategoryId: Long) = questionDao.getQuestions(gameCategoryId)

    fun addQuestion(question: Question) {
        CoroutineScope(Dispatchers.IO).launch {
            question.id = questionDao.addQuestion(question)
        }
    }

    fun updateQuestion(question: Question) {
        CoroutineScope(Dispatchers.IO).launch {
            questionDao.updateQuestion(question)
        }
    }

    fun deleteQuestion(question: Question) {
        CoroutineScope(Dispatchers.IO).launch {
            questionDao.deleteQuestion(question)
        }
    }

    private suspend fun starterData() {
        val historyId = gameCategoryDao.addGameCategory(GameCategory(categoryName = "History"))
        questionDao.addQuestion(
            Question(
                text = "Who was the first president of the United States?",
                answerOptions = listOf(
                    "George Washington",
                    "Thomas Jefferson",
                    "Abraham Lincoln",
                    "Benjamin Franklin"
                ),
                correctAnswer = "George Washington",
                categoryId = historyId,
                type = QuestionType.MC
            )
        )

        val geographyId = gameCategoryDao.addGameCategory(GameCategory(categoryName = "Geography"))
        questionDao.addQuestion(
            Question(
                text = "What is the capital of Japan?",
                answerOptions = listOf("Beijing", "Seoul", "Tokyo", "Bangkok"),
                correctAnswer = "Tokyo",
                categoryId = geographyId,
                type = QuestionType.MC
            )
        )

        val scienceMathId = gameCategoryDao.addGameCategory(GameCategory(categoryName = "Science & Math"))
        questionDao.addQuestion(
            Question(
                text = "What planet is known as the Red Planet?",
                answerOptions = listOf("Venus", "Mars", "Jupiter", "Mercury"),
                correctAnswer = "Mars",
                categoryId = scienceMathId,
                type = QuestionType.MC
            )
        )

        val popCultureId = gameCategoryDao.addGameCategory(GameCategory(categoryName = "Pop Culture"))
        questionDao.addQuestion(
            Question(
                text = "Which superhero is known as the Dark Knight?",
                answerOptions = listOf("Superman", "Batman", "Spider-Man", "Iron Man"),
                correctAnswer = "Batman",
                categoryId = popCultureId,
                type = QuestionType.MC
            )
        )

        val sportsGamesId = gameCategoryDao.addGameCategory(GameCategory(categoryName = "Sports & Games"))
        questionDao.addQuestion(
            Question(
                text = "How many players are on a standard basketball team on the court at one time?",
                answerOptions = listOf("4", "5", "6", "7"),
                correctAnswer = "5",
                categoryId = sportsGamesId,
                type = QuestionType.MC
            )
        )

        val literatureId = gameCategoryDao.addGameCategory(GameCategory(categoryName = "Literature"))
        questionDao.addQuestion(
            Question(
                text = "Who wrote 'Romeo and Juliet'?",
                answerOptions = listOf(
                    "Charles Dickens",
                    "William Shakespeare",
                    "Mark Twain",
                    "Jane Austen"
                ),
                correctAnswer = "William Shakespeare",
                categoryId = literatureId,
                type = QuestionType.MC
            )
        )

        val mixedKnowledgeId = gameCategoryDao.addGameCategory(GameCategory(categoryName = "Mixed Knowledge"))
        questionDao.addQuestion(
            Question(
                text = "True or False: Water boils at 100 degrees Celsius at sea level.",
                answerOptions = listOf("True", "False"),
                correctAnswer = "True",
                categoryId = mixedKnowledgeId,
                type = QuestionType.TF
            )
        )
    }
}