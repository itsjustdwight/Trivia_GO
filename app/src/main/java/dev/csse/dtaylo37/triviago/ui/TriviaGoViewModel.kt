package dev.csse.dtaylo37.triviago.ui

import androidx.lifecycle.ViewModel

class TriviaGoViewModel : ViewModel() {
    val gameCategoryList = mutableListOf<String>("History", "Geography", "Science & Math",
        "Pop Culture", "Sports & Games", "Literature", "Mixed Knowledge")
}