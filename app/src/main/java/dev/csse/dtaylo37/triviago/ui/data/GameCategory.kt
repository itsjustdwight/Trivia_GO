package dev.csse.dtaylo37.triviago.ui.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameCategory(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var categoryName: String = "",
)