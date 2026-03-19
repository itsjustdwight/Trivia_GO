package dev.csse.dtaylo37.triviago.ui

sealed class Route(val path: String) {
    data object Opening : Route("opening")
    data object Home : Route("home")
    data object Loading : Route("loading/{category}/{seconds}") {
        fun create(category: String, seconds: Int) = "loading/$category/$seconds"
    }
    data object MultipleChoice : Route("question/mc")
    data object FillBlank : Route("question/fill")
    data object Matching : Route("question/match")
    data object Rearrange : Route("question/rearrange")
    data object TrueFalse : Route("question/truefalse")
    data object Transition : Route("transition")
}