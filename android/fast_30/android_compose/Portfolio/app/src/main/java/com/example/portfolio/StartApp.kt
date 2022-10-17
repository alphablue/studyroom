package com.example.portfolio

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.portfolio.ui.common.notification.NotificationBuilder
import com.example.portfolio.ui.navigation.Sections
import com.example.portfolio.ui.navigation.applicationNavGraph
import com.example.portfolio.ui.navigation.rememberApplicationNavState
import com.example.portfolio.ui.screen.home.detailview.OrderDialog
import com.example.portfolio.ui.theme.PortfolioTheme
import com.example.portfolio.ui.theme.secondaryBlue

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val GOOGLE_MAP = "GoogleMap"
    const val LOGIN_PAGE = "Login"
    const val CART_PAGE = "Cart"
}

@Composable
fun StartApp(
    activityViewModel: MainActivityViewModel
) {
    PortfolioTheme {
        val appState = rememberApplicationNavState()
        val context = LocalContext.current
        val noti = NotificationBuilder(context)
        var dialogState by remember { mutableStateOf(false)}
        val floatState = activityViewModel.floatingState

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if (appState.shouldShowBottomBar) {
                    ApplicationBottomBar(
                        tabs = appState.bottomBarTabs,
                        currentRoute = appState.currentRoute!!,
                        navigateToRoute = appState::navigateToBottomBarRoute,
                        navController = appState.navController,
                        loginState = activityViewModel.loginState
                    )
                }
            },
            floatingActionButton = {

                when(floatState) {
                    FloatingState.NONE -> {}
                    FloatingState.ORDER -> {
                        val userId = activityViewModel.userInfo?.id ?: ""

                        val cartCount = activityViewModel.userCartMap
                            .filterKeys { userId in it }
                            .map { it.value }
                            .size

                        FloatingActionButton(
                            backgroundColor = MaterialTheme.colors.primary,
                            shape = RoundedCornerShape(14.dp),
                            onClick = {
                                noti.createDeliveryNotificationChannel(
                                    true, "알림 테스트 중", "알림 테스트 내용"
                                )
                            }) {
                            BadgedBox(
                                badge = {
                                    Badge(
                                        contentColor = Color.White,
                                        backgroundColor = secondaryBlue
                                    ) { Text(text = cartCount.toString()) }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.ShoppingCart,
                                    contentDescription = "your shopping cart"
                                )
                            }
                        }
                    }
                }
            }
        ) { innerPaddingModifier ->
            NavHost(
                navController = appState.navController,
                startDestination = MainDestinations.HOME_ROUTE,
                modifier = Modifier.padding(innerPaddingModifier)
            ) {
                applicationNavGraph(
                    upPress = appState::upPress,
                    goMap = appState::navigateToGoogleMap,
                    goLogin = appState::navigateToLoginPage,
                    goCart = appState::navigateToCart,
                    goRoute = appState::navigateToRoute,
                    addUriOfBackStack = appState::addUriOfBackStack,
                    getUriOfrPreviousStack = appState::getUriOfrPreviousStack,
                    activityViewModel
                )
            }
        }

        if(dialogState){
            OrderDialog(
                dialogStateCallBack = { state -> dialogState = state },
                confirmButtonContent = "로그인하기",
                dialogMainContent = "로그인이 필요합니다.",
                confirmEvent = {
                    appState.navController.navigate(
                        MainDestinations.LOGIN_PAGE
                    )
                    dialogState = false
                }
            )
        }
    }
}

@Composable
fun ApplicationBottomBar(
    tabs: Array<Sections>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit,
    navController: NavController,
    loginState: Boolean
) {

    // TODO 커스텀 네비게이션을 활용하기 위해 필요함
//    val routes = remember { tabs.map { it.route }}
//    val currentSection = tabs.first { it.route == currentRoute }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val paddingValues = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    var dialogState by remember { mutableStateOf(false) }

    BottomNavigation(
        modifier = Modifier.padding(bottom = paddingValues),
    ) {
        tabs.forEach { screen ->
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    Log.d(
                        "checkCurrentRoute",
                        "${currentDestination?.hierarchy?.any { it.route == screen.route } == true}, where my route? :: $currentRoute")
                    if (screen.title == Sections.Like.title) {
                        if (loginState) {
                            navigateToRoute(screen.route)
                        } else {
                            dialogState = true
                        }
                    } else {
                        navigateToRoute(screen.route)
                    }

                },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title
                    )
                }
            )
        }
    }

    if (dialogState) {
        OrderDialog(
            dialogStateCallBack = { dialogState = it },
            dialogMainContent = "로그인이 필요합니다.",
            confirmButtonContent = "로그인"
        ) {
            dialogState = false
            navController.navigate(MainDestinations.LOGIN_PAGE)
        }
    }
}