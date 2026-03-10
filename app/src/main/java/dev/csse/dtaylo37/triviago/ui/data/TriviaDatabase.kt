package dev.csse.dtaylo37.triviago.ui.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        Question::class,
        GameCategory::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class TriviaDatabase : RoomDatabase() {

    abstract fun QuestionDao(): QuestionDao
    abstract fun GameCategoryDao(): GameCategoryDao
}