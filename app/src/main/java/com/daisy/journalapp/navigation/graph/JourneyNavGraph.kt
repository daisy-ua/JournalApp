package com.daisy.journalapp.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.daisy.journalapp.navigation.AuthNavigationActions
import com.daisy.journalapp.navigation.Route

@Composable
fun JourneyNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    val authActions = remember(navController) { AuthNavigationActions(navController) }

    NavHost(
        navController = navController,
        startDestination = Route.AuthGraph
    ) {
        authGraph(navController, navActions = authActions)
    }
}