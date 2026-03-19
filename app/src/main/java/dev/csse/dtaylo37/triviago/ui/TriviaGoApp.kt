package dev.csse.dtaylo37.triviago.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.csse.dtaylo37.triviago.ui.data.TriviaRepository
import kotlinx.coroutines.launch

@Composable
fun TriviaGoApp(
    triviaRepository: TriviaRepository
) {
    val nav = rememberNavController()
    val viewModel: TriviaGoViewModel = viewModel(
        factory = TriviaGoViewModelFactory(triviaRepository)
    )

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
                        viewModel.viewModelScope.launch {
                            viewModel.selectCategory(category)
                            nav.navigate(Route.Loading.create(category, 3))
                        }
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

                        if (viewModel.questions.isNotEmpty()) {
                            nav.navigate(viewModel.routeForCurrentQuestion()) {
                                popUpTo(Route.Home.path) { inclusive = false }
                            }
                        }
                    }
                )
            }

            composable(Route.MultipleChoice.path) {
                MultipleChoiceQuestionScreen(
                    viewModel = viewModel,
                    onSubmit = {
                        if (viewModel.lastCorrect != null) {
                            nav.navigate(Route.Transition.path)
                        } else {
                            viewModel.submitAnswer()
                        }
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
                        if (viewModel.lastCorrect != null) {
                            nav.navigate(Route.Transition.path)
                        } else {
                            // Logic handled in screen (submitManualResult)
                        }
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
                        if (viewModel.lastCorrect != null) {
                            nav.navigate(Route.Transition.path)
                        } else {
                            // Logic handled in screen (submitManualResult)
                        }
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
                        if (viewModel.lastCorrect != null) {
                            nav.navigate(Route.Transition.path)
                        } else {
                            viewModel.submitAnswer()
                        }
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
        }
    }
}

// one route for question that takes the question id and then the first thing it does it look at question type
// and use 'when question type' and then depending on the question type do the corresponding screen