package dev.csse.dtaylo37.triviago.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dev.csse.dtaylo37.triviago.ui.theme.TriviaGOTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriviaGoAppBar(title: String) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Preview
@Composable
fun TriviaGoAppBarPreview() {
    TriviaGOTheme {
        Scaffold(
            topBar = {
                TriviaGoAppBar("Welcome Player")
            }
        ) {
            innerPadding ->
            Box(modifier = Modifier.padding(innerPadding))
        }
    }
}