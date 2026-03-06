package dev.csse.dtaylo37.triviago

import androidx.compose.ui.graphics.Color

enum class GameCategory(
    val displayName: String,
    val iconRes: Int,
    val backgroundColor: Color
) {
    HISTORY("History", R.drawable.history_icon, Color(0xFFE06659)),
    GEOGRAPHY("Geography", R.drawable.geography_icon, Color(0xFF605371)),
    SCIENCEMATH("Science & Math", R.drawable.sciencemath_icon, Color(0xFF4CB3A0)),
    POPCULTURE("Pop Culture", R.drawable.popculture_icon, Color(0xFF2D9DA8)),
    SPORTSSGAMES("Sports & Games", R.drawable.sportsgame_icon, Color(0xFFE7C648)),
    LITERATURE("Literature", R.drawable.literature_icon, Color(0xFF7CBC51)),
    MIXEDKNOWLEDGE("Mixed Knowledge", R.drawable.history_icon, Color(0xFF5B4D4D))
}

//Museum by mieestore from <a href="https://thenounproject.com/browse/icons/term/museum/" target="_blank" title="Museum Icons">Noun Project</a> (CC BY 3.0)
// Geography by Setyawan Guntoro from <a href="https://thenounproject.com/browse/icons/term/geography/" target="_blank" title="Geography Icons">Noun Project</a> (CC BY 3.0)
// Calculator by Yosua Bungaran from <a href="https://thenounproject.com/browse/icons/term/calculator/" target="_blank" title="Calculator Icons">Noun Project</a> (CC BY 3.0)
// Disco Ball by Daouna Jeong from <a href="https://thenounproject.com/browse/icons/term/disco-ball/" target="_blank" title="Disco Ball Icons">Noun Project</a> (CC BY 3.0)
// sports games by Juicy Fish from <a href="https://thenounproject.com/browse/icons/term/sports-games/" target="_blank" title="sports games Icons">Noun Project</a> (CC BY 3.0)
// literature by Meko from <a href="https://thenounproject.com/browse/icons/term/literature/" target="_blank" title="literature Icons">Noun Project</a> (CC BY 3.0)
// Television Quiz by NeueDeutsche from <a href="https://thenounproject.com/browse/icons/term/television-quiz/" target="_blank" title="Television Quiz Icons">Noun Project</a> (CC BY 3.0)