package com.example.portfolio.ui.navigation

import android.util.Log
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.portfolio.MainDestinations
import com.example.portfolio.ui.screen.cart.Cart
import com.example.portfolio.ui.screen.home.Home
import com.example.portfolio.ui.screen.profile.Profile
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.ui.screen.home.HomeViewModel
import com.example.portfolio.ui.screen.home.detailview.ListItemDetailView
import com.example.portfolio.ui.screen.home.detailview.detailRout


fun NavGraphBuilder.addHomeGraph(
    modifier: Modifier = Modifier,
    navController: NavController,
    activityViewModel: MainActivityViewModel
) {
    composable(Sections.HOME.route) { from ->
        val homeViewModel = hiltViewModel<HomeViewModel>()
        Home(modifier,
            navController,
            activityViewModel,
            homeViewModel)
    }
    composable(Sections.CART.route) { from ->
        Cart(modifier)
        Log.d("navigationTest", "cart $from")
    }
    composable(Sections.PROFILE.route) { from ->
        Profile(modifier)
        Log.d("navigationTest", "profile $from")
    }
}

fun NavGraphBuilder.applicationNavGraph(
    upPress: () -> Unit,
    controller: NavController,
    activityViewModel: MainActivityViewModel
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = Sections.HOME.route
    ) {
        addHomeGraph(activityViewModel = activityViewModel, navController = controller)
    }

    composable(
        route = "${MainDestinations.HOME_ROUTE}/$detailRout"
    ){
        ListItemDetailView()
    }
}

