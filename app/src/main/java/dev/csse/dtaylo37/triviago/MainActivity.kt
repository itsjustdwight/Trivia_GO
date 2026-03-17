package dev.csse.dtaylo37.triviago

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.csse.dtaylo37.triviago.ui.TriviaGoApp
import dev.csse.dtaylo37.triviago.ui.data.TriviaRepository
import dev.csse.dtaylo37.triviago.ui.theme.TriviaGOTheme

class MainActivity : ComponentActivity() {
    private lateinit var triviaRepository: TriviaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        triviaRepository = TriviaRepository(applicationContext)

        enableEdgeToEdge()
        setContent {
            TriviaGOTheme {
                TriviaGoApp(triviaRepository = triviaRepository)
            }
        }
    }
}