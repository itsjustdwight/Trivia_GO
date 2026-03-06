package dev.csse.dtaylo37.triviago.ui.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TriviaRepository(context : Context) {
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

    private fun starterData() {}
}