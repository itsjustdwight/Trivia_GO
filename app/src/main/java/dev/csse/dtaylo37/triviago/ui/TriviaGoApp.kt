package dev.csse.dtaylo37.triviago.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType

@Composable
fun TriviaGoApp(
    viewModel: TriviaGoViewModel = viewModel()
) {
    val nav = rememberNavController()

    Scaffold(
        topBar = {}
    ) { innerPadding ->

        NavHost(
            navController = nav,
            startDestination = Route.Opening.path,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Route.Opening.path) {
                OpeningScreen(
                    onContinue = { nav.navigate(Route.Home.path) }
                )
            }

            composable(Route.Home.path) {
                ChooseGameCategoryScreen(
                    onCategorySelected = { category ->
                        viewModel.selectCategory(category)
                        nav.navigate(Route.Loading.create(category, 3))
                    },
                    onProfileSettings = {
                        nav.navigate(Route.ProfileSettings.path)
                    }
                )
            }

            composable(
                route = Route.Loading.path,
                arguments = listOf(
                    navArgument("category") { type = NavType.StringType },
                    navArgument("seconds") { type = NavType.IntType }
                )
            ) { backStack ->
                val category = backStack.arguments?.getString("category") ?: ""
                val seconds = backStack.arguments?.getInt("seconds") ?: 3

                LoadingScreen(
                    category = category,
                    startSeconds = seconds,
                    onBack = { nav.popBackStack() },
                    onDone = {
                        viewModel.startGame()
                        nav.navigate(viewModel.routeForCurrentQuestion()) {
                            popUpTo(Route.Home.path) { inclusive = false }
                        }
                    }
                )
            }

            composable(Route.MultipleChoice.path) {
                MultipleChoiceQuestionScreen(
                    viewModel = viewModel,
                    onSubmit = {
                        viewModel.submitAnswer()
                        nav.navigate(Route.Transition.path)
                    },
                    onQuitHome = {
                        nav.navigate(Route.Home.path) {
                            popUpTo(Route.Home.path) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(Route.FillBlank.path) {
                FillInTheBlankQuestionScreen(
                    viewModel = viewModel,
                    onSubmit = {
                        viewModel.submitAnswer()
                        nav.navigate(Route.Transition.path)
                    },
                    onQuitHome = {
                        nav.navigate(Route.Home.path) {
                            popUpTo(Route.Home.path) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(Route.Matching.path) {
                MatchingQuestionScreen(
                    viewModel = viewModel,
                    onSubmit = {
                        viewModel.submitAnswer()
                        nav.navigate(Route.Transition.path)
                    },
                    onQuitHome = {
                        nav.navigate(Route.Home.path) {
                            popUpTo(Route.Home.path) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(Route.Rearrange.path) {
                RearrangeOrderQuestionScreen(
                    viewModel = viewModel,
                    onSubmit = {
                        viewModel.submitAnswer()
                        nav.navigate(Route.Transition.path)
                    },
                    onQuitHome = {
                        nav.navigate(Route.Home.path) {
                            popUpTo(Route.Home.path) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(Route.TrueFalse.path) {
                TrueOrFalseQuestionScreen(
                    viewModel = viewModel,
                    onSubmit = {
                        viewModel.submitAnswer()
                        nav.navigate(Route.Transition.path)
                    },
                    onQuitHome = {
                        nav.navigate(Route.Home.path) {
                            popUpTo(Route.Home.path) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(Route.Transition.path) {
                NextQuestionTransitionScreen(
                    viewModel = viewModel,
                    onNext = {
                        val finished = viewModel.nextQuestion()
                        if (finished) {
                            nav.navigate(Route.Home.path) {
                                popUpTo(Route.Home.path) {
                                    inclusive = true
                                }
                            }
                        } else {
                            nav.navigate(viewModel.routeForCurrentQuestion()) {
                                popUpTo(Route.Transition.path) { inclusive = true }
                            }
                        }
                    },
                    onHome = {
                        viewModel.reset()
                        nav.navigate(Route.Home.path) {
                            popUpTo(Route.Home.path) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(Route.ProfileSettings.path) {
                ProfileSettingsScreen(
                    viewModel = viewModel,
                    onBackHome = { nav.popBackStack() }
                )
            }
        }
    }
}

// one route for question that takes the question id and then the first thing it does it look at question type
// and use 'when question type' and then depending on the question type do the corresponding screen