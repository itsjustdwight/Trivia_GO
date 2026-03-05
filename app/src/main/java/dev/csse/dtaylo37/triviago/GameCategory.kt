package dev.csse.dtaylo37.triviago

import androidx.compose.ui.graphics.Color

enum class GameCategory(
    val displayName: String,
    val iconRes: Int,
    val backgroundColor: Color
) {
    HISTORY("History", R.drawable.history_icon, Color(0xFFE06659)),
    GEOGRAPHY("Geography", R.drawable.history_icon, Color(0xFF605371)),
    SCIENCEMATH("Science & Math", R.drawable.history_icon, Color(0xFF4CB3A0)),
    POPCULTURE("Pop Culture", R.drawable.history_icon, Color(0xFF2D9DA8)),
    SPORTSSGAMES("Sports & Games", R.drawable.history_icon, Color(0xFFE7C648)),
    LITERATURE("Literature", R.drawable.history_icon, Color(0xFF7CBC51)),
    MIXEDKNOWLEDGE("Mixed Knowledge", R.drawable.history_icon, Color(0xFF5B4D4D))
}

//Museum by mieestore from <a href="https://thenounproject.com/browse/icons/term/museum/" target="_blank" title="Museum Icons">Noun Project</a> (CC BY 3.0)