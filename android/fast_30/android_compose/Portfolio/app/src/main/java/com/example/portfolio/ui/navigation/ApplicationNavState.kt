package com.example.portfolio.ui.navigation

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


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
                launchSingleTop = true
                restoreState = true

                Log.d("checkBottomBar", "$route , current: $currentRoute")
                popUpTo(findStartDestination(navController.graph).id) {
                    saveState = true
                }
            }
        }
    }

}

enum class Sections(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    HOME("home", Icons.Outlined.Home, "home/appHome"),
    CART("cart", Icons.Outlined.ShoppingCart, "home/cart"),
    PROFILE("profile", Icons.Outlined.Person, "home/profile")
}

/**
 * State 가 Resume 이면 이미 클릭된 상태인 것
 * */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED


private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}