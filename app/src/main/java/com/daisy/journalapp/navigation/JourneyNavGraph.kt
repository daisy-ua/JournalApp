package com.daisy.journalapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.daisy.journalapp.authentication.ui.auth.AuthScreen
import com.daisy.journalapp.authentication.ui.login.LoginScreen
import com.daisy.journalapp.authentication.ui.register.RegisterScreen

@Composable
fun JourneyNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Route.AuthGraph
    ) {
        authGraph(navController)
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation<Route.AuthGraph>(
        startDestination = AuthDestinations.Auth
    ) {
        composable<AuthDestinations.Auth> {
            AuthScreen(
                onLogInClick = {
                    navController.navigate(AuthDestinations.Login)
                },
                onSignUpClick = {
                    navController.navigate(AuthDestinations.Register)
                }
            )
        }

        composable<AuthDestinations.Login> {
            LoginScreen(
                onSignUpClick = {
                    navController.navigate(AuthDestinations.Register) {
                        popUpTo(AuthDestinations.Register)
                        launchSingleTop = true
                    }
                },
                onUpClick = {
                    navController.navigateUp()
                }
            )
        }

        composable<AuthDestinations.Register> {
            RegisterScreen(
                onLogInClick = {
                    navController.navigate(AuthDestinations.Login) {
                        popUpTo(AuthDestinations.Login)
                        launchSingleTop = true
                    }
                },
                onUpClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}