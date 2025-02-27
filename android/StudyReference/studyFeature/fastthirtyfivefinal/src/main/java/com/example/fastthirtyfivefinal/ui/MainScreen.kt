package com.example.fastthirtyfivefinal.ui

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.fastthirtyfivefinal.R
import com.example.fastthirtyfivefinal.designsystem.icon.ThirtyFiveIcons
import com.example.fastthirtyfivefinal.ui.screen.category.categorySection
import com.example.fastthirtyfivefinal.ui.screen.main.mainSection
import com.example.fastthirtyfivefinal.ui.screen.mypage.myPageSection
import com.example.fastthirtyfivefinal.ui.theme.StudyReferenceTheme
import com.example.fastthirtyfivefinal.util.TimeZoneMonitor
import com.example.fastthirtyfivefinal.viewmodel.MainViewModelOld
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.TimeZone
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
fun EntryThirtyFiveAppNew(
    appState: ThirtyFiveAppNewState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
){
    val snackbarHostState = remember { androidx.compose.material3.SnackbarHostState() }

    ThirtyFiveAppNew(
        appState = appState,
        snackbarHostState = snackbarHostState,
        windowAdaptiveInfo = windowAdaptiveInfo
    )
}


@Composable
internal fun ThirtyFiveAppNew(
    appState: ThirtyFiveAppNewState,
    /**
     *  한번에 최대 하나의 스낵바를 표시하도록 보장하는 것, 다른 스택바가 이미 표시되어 있는 동안 다른 스택바를 호출하게 되면 잠시 일시 중단 되고
     *  이후 이전 스택바의 표출이 끝나면 보여지게 된다. 스낵바의 대기열을 세부적으로 제어할 수 있는 기능을 제공한다.
     * */
    snackbarHostState: androidx.compose.material3.SnackbarHostState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    val currentDestination = appState.currentDestination

    ThirtyFiveNavigationSuiteScaffold(
        navigationSuiteItem = {
            appState.mainTopLevelDestination.forEach { destination ->
                val selected = currentDestination
                    .isRouteInHierarchy(destination.route)

                item(
                    selected = selected,
                    onClick = { appState.navigateToTopLevelDestination(destination) },
                    icon = {
                        Icon(
                            imageVector = destination.unselectedIcon,
                            contentDescription = null,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = destination.selectedIcon,
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(destination.screenName)) }, // 아이콘 밑에 텍스트를 넣는 것
                    modifier = Modifier
                )
            }
        },
        windowAdaptiveInt = windowAdaptiveInfo
    ) {
        androidx.compose.material3.Scaffold(
            modifier = modifier,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = { androidx.compose.material3.SnackbarHost(snackbarHostState) }
        ) { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .windowInsetsPadding(
                        // sdk 35 의 edge-to-edge 관련하여 새로 추가된 api 로 그려지는 공간에 대하여 콘텐츠가 가려지지 않도록 보장하는 기능을 제공한다.
                        WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
                    )
            ) {
                val destination = appState.currentTopLevelDestination
                var shouldShowTopAppBar = false

                if (destination != null) {
                    shouldShowTopAppBar = true
                    // TODO : TopAbbBar 디자이 설정
                }

                Box(
                    modifier = Modifier.consumeWindowInsets(
                        if (shouldShowTopAppBar) {
                            WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                        } else {
                            WindowInsets(0, 0, 0, 0)
                        }
                    )
                ) {
                    ThirtyFiveNavHostNew(
                        appState = appState,
                        onShowSnackbar = { message, action ->
                            snackbarHostState.showSnackbar(
                                message = message,
                                actionLabel = action,
                                duration = androidx.compose.material3.SnackbarDuration.Short
                            ) == androidx.compose.material3.SnackbarResult.ActionPerformed
                        }
                    )
                }

            }
        }
    }
}

@Composable
fun ThirtyFiveNavigationSuiteScaffold(
    navigationSuiteItem: ThirtyFiveNavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    windowAdaptiveInt: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    content: @Composable () -> Unit
) {
    // adaptive screen info 를 가져오는 것
    val layoutType = NavigationSuiteScaffoldDefaults
        .calculateFromAdaptiveInfo(windowAdaptiveInt)

    // 사용자의 화면의 크기별로 적용하는 color 팔렛트
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
        ),
        layoutType = layoutType,
        modifier = modifier
    ) {
        content()
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
    // hilt-navigation-compose 를 추가 해줘야지만 사용 가능하다.
    val viewModel = hiltViewModel<MainViewModelOld>()

    val scaffoldState = rememberScaffoldState()

    // 일반적인 사용법? 강의에서 사용한 방법
    val navControllerOlder = rememberNavController()

    Scaffold(
        // 상단 바
        topBar = {
            HeaderOld(viewModel)
        },
        scaffoldState = scaffoldState, // material3 에서는 또 없어짐
        bottomBar = {
            MainBottomNavigationBarOlder(navControllerOlder)
        },
    ) { paddingValue ->
        Column(
            modifier = Modifier.padding(paddingValue)
        ) {
            MainNavigationScreenOld(navControllerOlder)
        }
    }
}

@Composable
fun HeaderOld(
    viewModel: MainViewModelOld
) {
    TopAppBar(
        title = { Text("My App") },
        actions = {
            IconButton(
                onClick = {
                    viewModel.openSearchForm()
                }
            ) {
                Icon(Icons.Filled.Search, "SearchIcon")
            }
        }
    )
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
                icon = { Icon(item.icon, item.name) },
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
    @StringRes val screenName: Int
) {
    MAIN(ThirtyFiveIcons.home, ThirtyFiveIcons.homeFilled, Main::class, R.string.bottom_nav_main),  // TODO : 각 리소스의 정보는 스크린 모듈이 만들어질 때 해당 모듈의 리소스에 넣어줘야 된다, 현재는 app 모듈에 들어가있음
    CATEGORY(ThirtyFiveIcons.airLineTicket, ThirtyFiveIcons.airLineTicketFilled, Category::class, R.string.bottom_nav_category),
    MY_PAGE(ThirtyFiveIcons.setting, ThirtyFiveIcons.settingFilled, MyPage::class, R.string.bottom_nav_my_page)
}

// 공식 가이드에서 직렬화 가능한 객체나 클래스를 사용하라는 권고 사항이 있다. -> In Compose, use a serializable object or class to define a route.
// tip : Use a data class for a route with arguments, and an object or data object for a route with no arguments.
@Serializable data object Main
@Serializable data object Category
@Serializable data object MyPage

fun NavController.navigateToMain(navOptions: NavOptions) = navigate(route = Main, navOptions = navOptions)
fun NavController.navigateToCategory(navOptions: NavOptions) = navigate(route = Category, navOptions = navOptions)
fun NavController.navigateToMyPage(navOptions: NavOptions) = navigate(route = MyPage, navOptions = navOptions)


// 강의 에서 사용하는 방식 -> material3 가 들어오면서 사용하는 방식이 바뀜
sealed class MainNavigationItem(
    val route: String,
    val icon: ImageVector,
    val name: String
) {
    data object Main: MainNavigationItem("Main", Icons.Filled.Home, "Main")
    data object Category: MainNavigationItem("Category", Icons.Filled.Star, "Category")
    data object MyPage: MainNavigationItem("MyPage", Icons.Filled.AccountBox, "MyPage")
}

@Composable
fun MainNavigationScreenOld(navController: NavHostController) {
    // NavHost 설계 도면을 수행 할 수 있도록 하는 모듈이고
    // navController 에서 createGraph() 를 통해서 만들어지거나 navGraphBuilder 로 만들어진 내용은 설계도 이다.
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
fun rememberFiveAppState(
    timeZoneMonitor: TimeZoneMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
): ThirtyFiveAppNewState {
    return remember(
        coroutineScope,
        navController,
    ) {
        ThirtyFiveAppNewState(
            navController = navController,
            coroutineScope = coroutineScope,
            timeZoneMonitor = timeZoneMonitor
        )
    }
}

class ThirtyFiveAppNewState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    timeZoneMonitor: TimeZoneMonitor
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)

    val currentDestination: NavDestination?
        @Composable get() {  //
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

    val currentTimeZone = timeZoneMonitor.currentTimeZone
        .stateIn(
            coroutineScope,
            SharingStarted.WhileSubscribed(5_000),
            TimeZone.currentSystemDefault()
        )

    fun navigateToTopLevelDestination(topLevelDestination: MainTopLevelDestination) {
        val topLevelNavOption = navOptions {

            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true  // saveInstance 적용
        }

        when (topLevelDestination) {
            MainTopLevelDestination.MAIN -> navController.navigateToMain(topLevelNavOption)
            MainTopLevelDestination.CATEGORY -> navController.navigateToCategory(topLevelNavOption)
            MainTopLevelDestination.MY_PAGE -> navController.navigateToMyPage(topLevelNavOption)
        }
    }
}

@Composable
fun ThirtyFiveNavHostNew(
    appState: ThirtyFiveAppNewState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController

    // 실제 navigation graph 를 생성하는 곳
    NavHost(
        navController = navController,
        startDestination = Main,
        modifier = modifier
    ) {
        mainSection()
        categorySection()
        myPageSection()
    }
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false