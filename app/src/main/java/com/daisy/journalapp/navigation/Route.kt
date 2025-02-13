package com.daisy.journalapp.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object AuthGraph : Route
}

sealed interface AuthDestinations : Route {

    @Serializable
    data object Auth : AuthDestinations

    @Serializable
    data object Login : AuthDestinations

    @Serializable
    data object Register : AuthDestinations
}