package com.example.portfolio

import androidx.compose.runtime.Composable
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

    }
}