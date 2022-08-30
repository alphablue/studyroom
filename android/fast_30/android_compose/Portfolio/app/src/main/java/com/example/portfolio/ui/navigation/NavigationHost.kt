package com.example.portfolio.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.portfolio.MainDestinations
import com.example.portfolio.ui.screen.dibs.Dibs
import com.example.portfolio.ui.screen.home.Home
import com.example.portfolio.ui.screen.profile.Profile


fun NavGraphBuilder.addHomeGraph(
    modifier: Modifier = Modifier
) {
    composable(Sections.HOME.route) { from ->
        Home()
    }
    composable(Sections.DIBS.route) { from ->
        Dibs()
    }
    composable(Sections.PROFILE.route) { from ->
        Profile()
    }
}

fun NavGraphBuilder.applicationNavGraph(
    upPress: () -> Unit
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = Sections.HOME.route
    ) {
        addHomeGraph()
    }
}

