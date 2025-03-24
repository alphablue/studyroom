package com.example.fastthirtyfivefinal.util

import android.os.Parcelable
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.example.fastthirtyfive_domain.model.ThirtyFiveCategory
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

object ThirtyFiveNavigationUtils {

    fun navigate(
        controller: NavHostController,
        routeName: String,
        args: Any? = null,
        backStackRouteName: String? = null,
        isLaunchSingleTop: Boolean = true,
        needToRestoreState: Boolean = true
    ) {
        var arguments = ""

        if(args != null) {
            when(args) {
                // https://plugins.gradle.org/plugin/org.jetbrains.kotlin.plugin.parcelize 플러긴을 설정 해줘야 한다.
                is Parcelable, is Serializable -> {
                    arguments = String.format("/%s", Json.encodeToJsonElement(args).toString().toUri())
                }

                is ThirtyFiveCategory -> {
                    arguments = String.format("/%s", Json.encodeToJsonElement(args).toString().toUri())
                }

                is ThirtyFiveProduct -> {
                    arguments = String.format("/%s", args.productId)
                }
            }
        }
        controller.navigate("$routeName$arguments") {
            if(backStackRouteName != null) {
                popUpTo(backStackRouteName) { saveState = true }
            }
            launchSingleTop = isLaunchSingleTop
            restoreState = needToRestoreState
        }
    }
}