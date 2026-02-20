package dev.csse.dtaylo37.triviago.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.csse.dtaylo37.triviago.ui.theme.TriviaGOTheme

@Composable
fun TriviaGoAppBar() {
    TopAppBar(
        title = { Text(
            "Welcome Player",
            style = MaterialTheme.typography.displayLarge
        )}
    )
}

@Preview
@Composable
fun TriviaGoAppBarPreview() {
    TriviaGOTheme {
        Scaffold(
            topBar = {
                TriviaGoAppBar()
            }
        ) {
            innerPadding ->
            Box(modifier = Modifier.padding(innerPadding))
        }
    }
}