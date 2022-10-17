package com.example.portfolio.ui.navigation

import android.net.Uri
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.portfolio.MainDestinations
import com.example.portfolio.ui.screen.home.detailview.detailRout
import com.example.portfolio.ui.screen.home.detailview.review.reviewRoute


@Composable
fun rememberApplicationNavState(
    navController: NavHostController = rememberNavController(),
) = remember(navController) {
    ApplicationNavState(navController)
}

@Stable
class ApplicationNavState(
    val navController: NavHostController,
) {

    val bottomBarTabs = Sections.values()
    private val bottomBarRoutes = bottomBarTabs.map{it.route}

    val shouldShowBottomBar: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes

    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun upPress() {
        navController.navigateUp()
    }

    fun navigateToBottomBarRoute(route: String) {
        if(route != currentRoute) {
            navController.navigate(route) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }

                launchSingleTop = true
                restoreState = true

                Log.d("checkBottomBar", "$route , current: $currentRoute")
            }
        }
    }

    fun navigateToGoogleMap(from: NavBackStackEntry) {
        if(from.lifecycleIsResumed()) {
            navController.navigate(MainDestinations.GOOGLE_MAP)
        }
    }

    fun navigateToLoginPage(from: NavBackStackEntry) {
        if(from.lifecycleIsResumed()) {
            navController.navigate(MainDestinations.LOGIN_PAGE)
        }
    }

    fun navigateToCart(from: NavBackStackEntry) {
        if(from.lifecycleIsResumed()) {
            navController.navigate(MainDestinations.CART_PAGE)
        }
    }

    fun navigateToReview(from: NavBackStackEntry) {
        if(from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.HOME_ROUTE}/$detailRout/$reviewRoute")
        }
    }

    fun navigateToRoute(from: NavBackStackEntry, route: String) {
        if(from.lifecycleIsResumed()){
            navController.navigate(route)
        }
    }

    fun addUriOfBackStack(key: String, value: Uri) {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set(key, value)
        navController.navigateUp()
    }

    fun getUriOfrPreviousStack(key: String, callback: (Uri) -> Unit) {
        val previousData = navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Uri>(key)

        previousData?.value?.let {
            callback(it)

            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.remove<Uri>(key)
        }
    }
}

enum class Sections(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    HOME("home", Icons.Outlined.Home, "home/appHome"),
    Like("like", Icons.Rounded.Favorite, "home/like"),
    PROFILE("profile", Icons.Rounded.Person, "home/profile")
}

/**
 * State 가 Resume 이면 이미 클릭된 상태인 것
 * */
private fun NavBackStackEntry.lifecycleIsResumed(): Boolean {
    Log.d("lifeCycleCheck", "lifecycle resumed check :: ${this.lifecycle.currentState}")
    return this.lifecycle.currentState == Lifecycle.State.RESUMED
}