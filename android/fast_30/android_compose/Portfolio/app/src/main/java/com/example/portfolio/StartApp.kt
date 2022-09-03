package com.example.portfolio

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.portfolio.ui.navigation.Sections
import com.example.portfolio.ui.navigation.applicationNavGraph
import com.example.portfolio.ui.navigation.rememberApplicationNavState
import com.example.portfolio.ui.theme.PortfolioTheme

object MainDestinations {
    const val HOME_ROUTE = "home"
}

@Composable
fun StartApp() {
    PortfolioTheme {
        val appState = rememberApplicationNavState()

        Scaffold(
            modifier= Modifier.fillMaxSize(),
            bottomBar = {
                if(appState.shouldShowBottomBar) {
                    ApplicationBottomBar(
                        tabs = appState.bottomBarTabs,
                        currentRoute = appState.currentRoute!!,
                        navigateToRoute = appState::navigateToBottomBarRoute,
                        navController = appState.navController
                    )
                }
            }
        ) { innerPaddingModifier ->
            NavHost(navController = appState.navController,
                startDestination = MainDestinations.HOME_ROUTE,
                modifier = Modifier.padding(innerPaddingModifier)
            ) {
                applicationNavGraph(
                    upPress = appState::upPress
                )
            }

        }
    }
}

@Composable
fun ApplicationBottomBar(
    tabs: Array<Sections>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit,
    navController: NavController
) {
    val routes = remember { tabs.map { it.route }}
    val currentSection = tabs.first { it.route == currentRoute }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val paddingValues = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    BottomNavigation(
        modifier = Modifier.padding(bottom = paddingValues),
    ) {
        tabs.forEach { screen ->
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any{it.route == screen.route} == true,
                onClick = {
                    Log.d("checkCurrentRoute","${currentDestination?.hierarchy?.any { it.route == screen.route } == true}")
                    navigateToRoute(screen.route) },
                icon = {
                    Icon(imageVector = screen.icon,
                    contentDescription = screen.title)
                }
            )
        }
    }
}