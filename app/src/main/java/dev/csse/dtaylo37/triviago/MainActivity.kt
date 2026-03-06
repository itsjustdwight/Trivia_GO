package dev.csse.dtaylo37.triviago

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.csse.dtaylo37.triviago.ui.*
import dev.csse.dtaylo37.triviago.ui.theme.TriviaGOTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TriviaGOTheme {
                TriviaGoApp()
            }
        }
    }
}
