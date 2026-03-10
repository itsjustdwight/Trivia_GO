package dev.csse.dtaylo37.triviago.ui.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class QuestionType {
    MC,
    FILL,
    MATCH,
    REARRANGE,
    TF
}

@Entity
data class Question(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var text: String = "",
    var answerOptions: List<String> = emptyList(),
    var correctAnswer: String = "",
    var categoryId: Long = 0,
    var type: QuestionType = QuestionType.MC
)
