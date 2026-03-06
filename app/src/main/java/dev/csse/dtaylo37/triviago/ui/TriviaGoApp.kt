package dev.csse.dtaylo37.triviago.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost

sealed class Routes {
    @Serializable
    data object ChooseGameCategory // home screen
}

@Composable
fun TriviaGoApp() {
    NavHost() {
        composable<Routes.ChooseGameCategory> { backstackEntry ->
            val gameCategory: Routes.ChooseGameCategory = backstackEntry.toRoute()

            Scaffold(
                topBar = {
                    TriviaGoAppBar()
                }
            ) { innerPadding ->
                if () {
                }
            }
        }
    }
}