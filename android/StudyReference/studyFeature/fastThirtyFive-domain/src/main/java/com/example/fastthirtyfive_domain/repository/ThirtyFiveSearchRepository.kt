package com.example.fastthirtyfive_domain.repository

import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.model.ThirtyFiveSearchFilter
import com.example.fastthirtyfive_domain.model.ThirtyFiveSearchKeyword
import kotlinx.coroutines.flow.Flow

interface ThirtyFiveSearchRepository {
    suspend fun search(searchKeyword: ThirtyFiveSearchKeyword, filters: List<ThirtyFiveSearchFilter>): Flow<List<ThirtyFiveProduct>>

    fun getSearchKeywords(): Flow<List<ThirtyFiveSearchKeyword>>
}

//interface Destination {
//    val route: String
//    val title: String
//    val deepLinks: List<NavDeepLink>  // android lib
//}
//
//interface DestinationArg<T>: Destination {
//    val argName: String
//    val arguments: List<NamedNavArgument>  // android lib
//
//    fun routeWithArgName() = "$route/{$arguments}"
//    fun navigateWithArg(itme: T): String
//    fun findArgument(navBackStackEntry: NavBackStackEntry) : T?  // android lib
//}
//
//object CategoryNav: DestinationArg<ThirtyFiveCategory> {
//    override val route: String = "category"
//    override val title: String = "카테고리"
//    override val argName: String = "category"
//    override val arguments: List<NamedNavArgument> = listOf(
//        navDeepLink { uriPattern = "$scheme$route/{$argName}"}
//    )
//
//    override val deepLinks: List<NavDeepLink> = navDeepLink { uriPattern = "$scheme$route/{$argName}"}
//
//    override fun findArgument(navBackStackEntry: NavBackStackEntry): ThirtyFiveCategory? {
//        val categoryString = navBackStackEntry.arguments?.getString(argName)
//        return GsonUtils.fromJson<ThirtyFiveCategory>
//    }
//
//    override fun navigateWithArg(itme: ThirtyFiveCategory): String {
//        val arg = GsonUtils.toJson(item)
//        return "$route/{$arg}"
//    }
//}