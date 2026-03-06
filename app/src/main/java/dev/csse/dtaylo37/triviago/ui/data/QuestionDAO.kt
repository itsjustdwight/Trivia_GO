package dev.csse.dtaylo37.triviago.ui.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Query("SELECT * FROM Question WHERE id = :id")
    fun getQuestion(id: Long): Flow<Question?>

    @Query("SELECT * FROM Question WHERE categoryId = :gameCategoryId ORDER BY id")
    fun getQuestions(gameCategoryId: Long): Flow<List<Question>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addQuestion(question: Question): Long

    @Update
    fun updateQuestion(question: Question)

    @Delete
    fun deleteQuestion(question: Question)
}
