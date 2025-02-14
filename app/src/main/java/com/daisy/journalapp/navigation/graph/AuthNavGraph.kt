package com.daisy.journalapp.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.daisy.journalapp.authentication.ui.auth.AuthScreen
import com.daisy.journalapp.authentication.ui.login.LoginScreen
import com.daisy.journalapp.authentication.ui.register.RegisterScreen
import com.daisy.journalapp.navigation.AuthDestinations
import com.daisy.journalapp.navigation.AuthNavigationActions
import com.daisy.journalapp.navigation.Route
import com.daisy.journalapp.navigation.slideInFromLeft
import com.daisy.journalapp.navigation.slideOutFromLeft
import com.daisy.journalapp.navigation.slideOutFromRight

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    navActions: AuthNavigationActions
) {
    navigation<Route.AuthGraph>(
        startDestination = AuthDestinations.Auth
    ) {
        authParentComposableWrapper<AuthDestinations.Auth> {
            AuthScreen(
                onLogInClick = navActions.navigateToLogin,
                onSignUpClick = navActions.navigateToRegister
            )
        }

        authChildComposableWrapper<AuthDestinations.Login>(
            isNavigationToParent = { isNavigatingToParent(navController) }
        ) {
            LoginScreen(
                onSignUpClick = navActions.navigateToRegister,
                onUpClick = navActions.navigateToAuth
            )
        }

        authChildComposableWrapper<AuthDestinations.Register>(
            isNavigationToParent = { isNavigatingToParent(navController) }
        ) {
            RegisterScreen(
                onLogInClick = navActions.navigateToLogin,
                onUpClick = navActions.navigateToAuth
            )
        }
    }
}


private inline fun <reified T : Any> NavGraphBuilder.authParentComposableWrapper(
    crossinline content: @Composable () -> Unit
) {
    composable<T>(
        exitTransition = slideOutFromLeft,
    ) {
        content()
    }
}

private inline fun <reified T : Any> NavGraphBuilder.authChildComposableWrapper(
    noinline isNavigationToParent: () -> Boolean,
    crossinline content: @Composable () -> Unit
) {
    composable<T>(
        enterTransition = slideInFromLeft,
        exitTransition = slideOutFromLeft,
        popEnterTransition = slideInFromLeft,
        popExitTransition = {
            if (isNavigationToParent()) {
                slideOutFromRight()
            } else {
                slideOutFromLeft()
            }
        }
    ) {
        content()
    }
}

private fun isNavigatingToParent(navController: NavController): Boolean {
    return navController.currentBackStackEntry?.destination?.route == AuthDestinations.Auth::class.qualifiedName
}