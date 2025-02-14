package com.daisy.journalapp.navigation

import androidx.navigation.NavHostController


class AuthNavigationActions(navController: NavHostController) {
    val navigateToAuth: () -> Unit = {
        navController.navigate(AuthDestinations.Auth) {
            popUpTo(AuthDestinations.Auth)
            launchSingleTop = true
        }
    }

    val navigateToLogin: () -> Unit = {
        navController.navigate(AuthDestinations.Login) {
            popUpTo(AuthDestinations.Login)
            launchSingleTop = true
        }
    }

    val navigateToRegister: () -> Unit = {
        navController.navigate(AuthDestinations.Register) {
            popUpTo(AuthDestinations.Register)
            launchSingleTop = true
        }
    }
}