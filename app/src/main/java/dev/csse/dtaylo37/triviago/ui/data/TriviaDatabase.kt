package dev.csse.dtaylo37.triviago.ui.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Question::class,
        GameCategory::class],
    version = 1
)
abstract class TriviaDatabase : RoomDatabase() {

    abstract fun QuestionDao(): QuestionDao
    abstract fun GameCategoryDao(): GameCategoryDao
}