package com.example.fastthirtyfivefinal.ui

import android.content.Context
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
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.credentials.GetCredentialRequest
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.example.fastthirtyfive_domain.model.ThirtyFiveCategory
import com.example.fastthirtyfivefinal.R
import com.example.fastthirtyfivefinal.designsystem.icon.ThirtyFiveIcons
import com.example.fastthirtyfivefinal.ui.category.ThirtyFiveCategoryScreen
import com.example.fastthirtyfivefinal.ui.main.ThirtyFiveMainCategoryScreen
import com.example.fastthirtyfivefinal.ui.main.ThirtyFiveMainHomeScreen
import com.example.fastthirtyfivefinal.ui.main.ThirtyFiveMyPageScreen
import com.example.fastthirtyfivefinal.ui.product_detail.ThirtyFiveProductDetailScreen
import com.example.fastthirtyfivefinal.ui.screen.category.categorySection
import com.example.fastthirtyfivefinal.ui.screen.main.mainSection
import com.example.fastthirtyfivefinal.ui.screen.mypage.myPageSection
import com.example.fastthirtyfivefinal.ui.search.ThirtyFiveSearchScreen
import com.example.fastthirtyfivefinal.util.TimeZoneMonitor
import com.example.fastthirtyfivefinal.viewmodel.MainViewModelOld
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.TimeZone
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.KClass

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
fun MainScreenOlder(
    googleSignInRequester: GetCredentialRequest,
    firebaseAuth: FirebaseAuth,
    activityContext: Context
) {
    // hilt-navigation-compose 를 추가 해줘야지만 사용 가능하다.
    val viewModel = hiltViewModel<MainViewModelOld>()

    val scaffoldState = rememberScaffoldState()

    // 일반적인 사용법? 강의에서 사용한 방법
    val navControllerOlder = rememberNavController()

    val navBackStackEntry by navControllerOlder.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route // 현재 탭을 가져 오는 방법

    Scaffold(
        // 상단 바
        topBar = {
            if(ThirtyFiveNavigationItem.MainNav.isMainRoute(currentRoute)) {
                MainHeaderOld(navControllerOlder, viewModel)
            }
        },
        scaffoldState = scaffoldState, // material3 에서는 또 없어짐
        bottomBar = {
            // main route 에서만 바텀 네비게이션 노출
            if(ThirtyFiveNavigationItem.MainNav.isMainRoute(currentRoute)) {
                MainBottomNavigationBarOlder(navControllerOlder, currentRoute)
            }
        },
    ) { paddingValue ->
        Column(
            modifier = Modifier.padding(paddingValue)
        ) {
            MainNavigationScreenOld(
                navController = navControllerOlder,
                mainViewModelOld = viewModel,
                googleSignInRequester = googleSignInRequester,
                firebaseAuth = firebaseAuth,
                activityContext = activityContext
            )
        }
    }
}

@Composable
fun MainHeaderOld(
    navController: NavHostController,
    viewModel: MainViewModelOld
) {
    TopAppBar(
        title = { Text("My App") },
        actions = {
            IconButton(
                onClick = {
                    viewModel.openSearchForm(navController)
                }
            ) {
                Icon(Icons.Filled.Search, "SearchIcon")
            }
        }
    )
}

@Composable
fun MainBottomNavigationBarOlder(
    navigationController: NavHostController,
    currentRoute: String?
) {
    val bottomNavigationItems = listOf(
        ThirtyFiveNavigationItem.MainNav.Home,
        ThirtyFiveNavigationItem.MainNav.Category,
        ThirtyFiveNavigationItem.MainNav.MyPage,
    )

    BottomNavigation(
        backgroundColor = Color(0xffff0000),
        contentColor = Color(0xff00ff00)
    ) {

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
// 강의가 진행됨에 따라 ThirtyFiveNavigationItem 으로 이동
//sealed class MainNavigationItem(
//    val route: String,
//    val icon: ImageVector,
//    val name: String
//) {
//    data object Main: MainNavigationItem("Main", Icons.Filled.Home, "Main")
//    data object Category: MainNavigationItem("Category", Icons.Filled.Star, "Category")
//    data object MyPage: MainNavigationItem("MyPage", Icons.Filled.AccountBox, "MyPage")
//}

@Composable
fun MainNavigationScreenOld(
    navController: NavHostController,
    mainViewModelOld: MainViewModelOld,
    googleSignInRequester: GetCredentialRequest,
    firebaseAuth: FirebaseAuth,
    activityContext: Context
) {
    // NavHost 설계 도면을 수행 할 수 있도록 하는 모듈이고
    // navController 에서 createGraph() 를 통해서 만들어지거나 navGraphBuilder 로 만들어진 내용은 설계도 이다.
    NavHost(
        navController = navController,
        startDestination = ThirtyFiveNavigationRouteName.MAIN_HOME
    ) {
        // composable 은 navigation 에 존재하는 route 기능을 제공하기 위함
        composable(ThirtyFiveNavigationRouteName.MAIN_HOME) {
            ThirtyFiveMainHomeScreen(navController, mainViewModelOld)
        }

        composable(ThirtyFiveNavigationRouteName.MAIN_CATEGORY) {
            ThirtyFiveMainCategoryScreen(mainViewModelOld, navController)
        }

        composable(ThirtyFiveNavigationRouteName.MAIN_MY_PAGE) {
            ThirtyFiveMyPageScreen(mainViewModelOld, googleSignInRequester, firebaseAuth, activityContext)
        }

        composable(ThirtyFiveNavigationRouteName.CATEGORY + "/{category}",
            // 아래의 의미는 route 에서 / 뒤에 {id} 로 설정한 값을 id 를 토대로 추출한다음에 backStackEntry에 argument 에 넣어준다는 의미이다.
            arguments = listOf(navArgument("category"){
                type = NavType.StringType
            })
        ) {
            it.arguments?.getString("category")?.let {categoryString ->
                val category = Json.decodeFromString<ThirtyFiveCategory>(categoryString)

                ThirtyFiveCategoryScreen(
                    navHostController = navController,
                    category = category
                )
            }
        }

        composable(ThirtyFiveNavigationRouteName.PRODUCT_DETAIL + "/{product}",
            arguments = listOf(navArgument("product") { type = NavType.StringType} )
        ) {
            it.arguments?.getString("product")?.let { productId ->
                // product 객체로 받아야 되는 경우 아래 처럼 사용, 현재는 id 만 받으므로 처리 불필요
//                val product = Json.decodeFromString<ThirtyFiveProduct>(productString)

                ThirtyFiveProductDetailScreen(productId)
            }
        }

        composable(ThirtyFiveNavigationRouteName.SEARCH) {
            ThirtyFiveSearchScreen(navController)
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