package com.example.portfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.portfolio.ui.screen.map.GoogleMapView
import com.example.portfolio.ui.screen.util.permission.PermissionCheck
import com.example.portfolio.ui.testview.TestView
import com.example.portfolio.ui.theme.PortfolioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PortfolioTheme {
                GoogleMapView()
            }
        }
    }
}
