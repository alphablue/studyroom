package com.example.portfolio.ui.navigation

import android.util.Log
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.MainDestinations
import com.example.portfolio.ui.screen.cart.Cart
import com.example.portfolio.ui.screen.like.Like
import com.example.portfolio.ui.screen.home.Home
import com.example.portfolio.ui.screen.home.HomeViewModel
import com.example.portfolio.ui.screen.home.detailview.ListItemDetailView
import com.example.portfolio.ui.screen.home.detailview.detailRout
import com.example.portfolio.ui.screen.login.LoginPage
import com.example.portfolio.ui.screen.map.GoogleMapView
import com.example.portfolio.ui.screen.profile.Profile

fun NavGraphBuilder.addHomeGraph(
    modifier: Modifier = Modifier,
    itemSelect: (NavBackStackEntry) -> Unit,
    goMap: (NavBackStackEntry) -> Unit,
    goLogin: (NavBackStackEntry) -> Unit,
    goCart: (NavBackStackEntry) -> Unit,
    activityViewModel: MainActivityViewModel
) {
    val deepLinkUri = "portfolio://test_deep_link"

    composable(Sections.HOME.route) { from ->
        val homeViewModel = hiltViewModel<HomeViewModel>()
        activityViewModel.floatingState = true

        Home(
            modifier,
            itemSelect = { poi ->
                itemSelect(from)
                activityViewModel.detailItem = poi
            },
            goMap = { goMap(from) },
            goCart = {goCart(from)},
            activityViewModel,
            homeViewModel
        )
    }
    composable(
        Sections.Like.route,
        deepLinks = listOf(
            navDeepLink { uriPattern = deepLinkUri }
        )
    ) { from ->
        activityViewModel.floatingState = false

        Like(sharedViewModel = activityViewModel, modifier)
        Log.d("navigationTest", "cart $from")
    }
    composable(Sections.PROFILE.route) { from ->
        activityViewModel.floatingState = false

        Profile(activityViewModel, goLogin = { goLogin(from) })
        Log.d("navigationTest", "profile $from")
    }
}

fun NavGraphBuilder.applicationNavGraph(
    upPress: () -> Unit,
    itemSelect: (NavBackStackEntry) -> Unit,
    goMap: (NavBackStackEntry) -> Unit,
    goLogin: (NavBackStackEntry) -> Unit,
    goCart: (NavBackStackEntry) -> Unit,
    activityViewModel: MainActivityViewModel
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = Sections.HOME.route
    ) {
        addHomeGraph(
            activityViewModel = activityViewModel,
            goMap = goMap,
            goLogin = goLogin,
            goCart = goCart,
            itemSelect = itemSelect
        )
    }

    composable(
        route = "${MainDestinations.HOME_ROUTE}/$detailRout"
    ) {
        activityViewModel.floatingState = false

        ListItemDetailView(
            activityViewModel,
            upPress = upPress,
            goLogin = { goLogin(it) }
        )
    }

    composable(
        route = MainDestinations.GOOGLE_MAP
    ) {
        activityViewModel.floatingState = false

        GoogleMapView(
            activityViewModel = activityViewModel,
            upPress = upPress
        )
    }

    composable(
        route = MainDestinations.LOGIN_PAGE
    ) {
        activityViewModel.floatingState = false

        LoginPage(
            sharedViewModel = activityViewModel,
            upPress = upPress
        )
    }

    composable(
        route = MainDestinations.CART_PAGE
    ) {
        activityViewModel.floatingState = false

        Cart(
            sharedViewModel = activityViewModel,
            upPress = upPress
        )
    }
}

