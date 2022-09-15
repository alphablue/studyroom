package com.example.portfolio.ui.navigation

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.portfolio.MainDestinations
import com.example.portfolio.ui.screen.cart.Cart
import com.example.portfolio.ui.screen.home.Home
import com.example.portfolio.ui.screen.profile.Profile
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.ui.screen.home.HomeViewModel


fun NavGraphBuilder.addHomeGraph(
    modifier: Modifier = Modifier,
    activityViewModel: MainActivityViewModel
) {
    composable(Sections.HOME.route) { from ->
        val homeViewModel = hiltViewModel<HomeViewModel>()
        Home(modifier, activityViewModel, homeViewModel)
    }
    composable(Sections.CART.route) { from ->
        Cart(modifier)
    }
    composable(Sections.PROFILE.route) { from ->
        Profile(modifier)
    }
}

fun NavGraphBuilder.applicationNavGraph(
    upPress: () -> Unit,
    activityViewModel: MainActivityViewModel
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = Sections.HOME.route
    ) {
        addHomeGraph(activityViewModel = activityViewModel)
    }
}

