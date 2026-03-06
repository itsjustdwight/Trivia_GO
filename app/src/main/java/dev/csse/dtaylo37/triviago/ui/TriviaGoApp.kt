package dev.csse.dtaylo37.triviago.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object Home

    @Serializable
    data class Loading(val category: String, val seconds: Int)

    @Serializable
    data object ProfileSettings

    @Serializable
    data object MultipleChoiceQuestion

    @Serializable
    data object FillInTheBlankQuestion

    @Serializable
    data object RearrangeTheOrderQuestion

    @Serializable
    data object TrueOrFalseQuestion

    @Serializable
    data object MatchingQuestion
}

@Composable
fun TriviaGoApp() {
    val navController = rememberNavController()
    val viewModel: TriviaGoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.Home
    ) {
        composable<Routes.Home> {
            ChooseGameCategoryScreen(
                onCategorySelected = { category ->
                    viewModel.selectCategory(category)
                    viewModel.startGame()
                },
                onProfileSettings = {
                    navController.navigate(Routes.ProfileSettings)
                }
            )
        }

        composable<Routes.ProfileSettings> {
            ProfileSettingsScreen(
                viewModel = viewModel,
                onBackHome = { navController.popBackStack() }
            )
        }

    }
}
