package com.example.portfolio

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import com.example.portfolio.ui.navigation.Sections
import com.example.portfolio.ui.navigation.applicationNavGraph
import com.example.portfolio.ui.navigation.rememberApplicationNavState
import com.example.portfolio.ui.theme.PortfolioTheme

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val DIBS_ROUTE = "dibs"
    const val PROFILE_ROUTE = "profile"
}

@Composable
fun StartApp() {
    PortfolioTheme {
        val appState = rememberApplicationNavState()

        Scaffold(
            bottomBar = {
                if(appState.shouldShowBottomBar) {
                    ApplicationBottomBar(
                        tabs = appState.bottomBarTabs,
                        currentRoute = appState.currentRoute!!,
                        navigateToRoute = appState::navigateToBottomBarRoute
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
) {
    val routes = remember { tabs.map { it.route }}
    val currentSection = tabs.first { it.route == currentRoute }

}