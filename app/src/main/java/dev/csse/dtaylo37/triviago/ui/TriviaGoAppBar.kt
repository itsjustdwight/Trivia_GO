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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.csse.dtaylo37.triviago.ui.theme.TriviaGOTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriviaGoAppBar() {
    CenterAlignedTopAppBar(
        title = { Text(
            "Welcome Player",
            style = MaterialTheme.typography.displaySmall
        ) },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.secondary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )

        // TODO: In the profile settings screen, this TopAppBar will
    //      contain "Profile Settings" t

        // TODO: In the loading and question screens, this TopAppBar will
    //      contain the game category name and icon
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