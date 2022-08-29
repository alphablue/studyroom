package com.example.portfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.portfolio.ui.screen.map.GoogleMapView
import com.example.portfolio.ui.screen.util.permission.PermissionCheck
import com.example.portfolio.ui.testview.TestView
import com.example.portfolio.ui.theme.PortfolioTheme
import com.example.portfolio.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val activityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PortfolioTheme {
                GoogleMapView(activityViewModel = activityViewModel)
            }
        }
    }
}
