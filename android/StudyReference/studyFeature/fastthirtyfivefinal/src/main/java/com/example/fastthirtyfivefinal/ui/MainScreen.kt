package com.example.fastthirtyfivefinal.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fastthirtyfivefinal.R
import com.example.fastthirtyfivefinal.ui.theme.StudyReferenceTheme
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StudyReferenceTheme {
        MainScreenOlder()
    }
}

/**
 * older 의 디자인은 material1~2 를 사용하는 경우 api 의 종류가 달라 구현 방법이 다르고
 * new 의 경운 material3 이상을 사용하는 경우로 api 가 개선된 것이다.
 * */

@Composable
fun ShowMainScreenNew() {

}

@Composable
fun MainScreenNew(
    navigationSuiteItem: ThirtyFiveNavigationSuiteScope.() -> Unit
) {
    val navController = rememberSaveable { mutableStateOf(MainTopLevelDestination.MAIN) }

    val navigationSuiteItemColors = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = NavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = NavigationDefaults.navigationContentColor(),
            selectedTextColor = NavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = NavigationDefaults.navigationContentColor(),
            indicatorColor = NavigationDefaults.navigationIndicatorColor()
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = NavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = NavigationDefaults.navigationContentColor(),
            selectedTextColor = NavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = NavigationDefaults.navigationContentColor(),
            indicatorColor = NavigationDefaults.navigationIndicatorColor()
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = NavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = NavigationDefaults.navigationContentColor(),
            selectedTextColor = NavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = NavigationDefaults.navigationContentColor()
        )
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {

            ThirtyFiveNavigationSuiteScope(
                navigationSuiteScope = this,
                navigationSuiteItemColors = navigationSuiteItemColors
            ).run(navigationSuiteItem)
        },
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContentColor = NavigationDefaults.navigationContentColor(),
            navigationRailContentColor = Color.Transparent
        )
    ) {

    }
}

class ThirtyFiveNavigationSuiteScope internal constructor(
    private val navigationSuiteScope: NavigationSuiteScope,
    private val navigationSuiteItemColors: NavigationSuiteItemColors
) {
    fun item(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        icon: @Composable () -> Unit,
        selectedIcon: @Composable () -> Unit = icon,
        label: @Composable (() -> Unit)? = null
    ) = navigationSuiteScope.item(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        icon = {
            if (selected) {
                selectedIcon()
            } else {
                icon()
            }
        },
        label = label,
        colors = navigationSuiteItemColors
    )
}

@Composable
fun MainScreenOlder() {
    val scaffoldState = rememberScaffoldState()

    // 일반적인 사용법? 강의에서 사용한 방법
    val navControllerOrder = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState, // material3 에서는 또 없어짐
        bottomBar = {
            MainBottomNavigationBarOlder(navControllerOrder)
        },
    ) { paddingValue ->
        Column(
            modifier = Modifier.padding(paddingValue)
        ) {
            MainNavigationScreenOld(navControllerOrder)
        }
    }
}


@Composable
fun MainBottomNavigationBarOlder(
    navigationController: NavHostController
) {
    val bottomNavigationItems = listOf(
        MainNavigationItem.Main,
        MainNavigationItem.Category,
        MainNavigationItem.MyPage,
    )

    BottomNavigation(
        backgroundColor = Color(0xffff0000),
        contentColor = Color(0xff00ff00)
    ) {
        val navBackStackEntry by navigationController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route // 현재 탭을 가져 오는 방법

        bottomNavigationItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navigationController.navigate(item.route) {
                        navigationController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true //

                        restoreState = true  // activity 나 fragment 에서 saveInstanceState 값을 넣어줄지 말지를 결정하는 것
                    }
                },
                icon = { Icon(painterResource(R.drawable.baseline_smart_button_24), contentDescription = item.route)}
            )
        }
    }
}

object NavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}

enum class MainTopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: KClass<*>,
    val screenName: String
) {
    MAIN(Main::class, "Main"),
    CATEGORY(Category::class, "Category"),
    MY_PAGE(MyPage::class, "MyPage")
}

@Serializable data object Main
@Serializable data object Category
@Serializable data object MyPage


// 강의 에서 사용하는 방식 -> material3 가 들어오면서 사용하는 방식이 바뀜
sealed class MainNavigationItem(
    val route: String,
    val name: String
) {
    data object Main: MainNavigationItem("Main", "Main")
    data object Category: MainNavigationItem("Category", "Category")
    data object MyPage: MainNavigationItem("MyPage", "MyPage")
}

@Composable
fun MainNavigationScreenOld(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MainNavigationItem.Main.route
    ) {
        // composable 은 navigation 에 존재하는 route 기능을 제공하기 위함
        composable(MainNavigationItem.Main.route) {
            Text(text = "Hello Main")
        }

        composable(MainNavigationItem.Category.route) {
            Text(text = "Hello Category")
        }

        composable(MainNavigationItem.MyPage.route) {
            Text(text = "Hello MyPage")
        }
    }
}

@Composable
fun ThirtyFiveApp() {

}

class ThirtyFiveAppState(
    val navController: NavHostController
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)

    val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry = navController.currentBackStackEntryFlow
                .collectAsState(initial = null)

            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val currentTopLevelDestination: MainTopLevelDestination?
        @Composable get() {
            return MainTopLevelDestination.entries.firstOrNull { mainTopLevelDestination ->
                currentDestination?.hasRoute(route = mainTopLevelDestination.route) == true
            }
        }

    val mainTopLevelDestination: List<MainTopLevelDestination> = MainTopLevelDestination.entries


}