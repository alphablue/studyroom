import com.android.build.api.dsl.ApplicationExtension
import com.example.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure


class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
//            with(pluginManager) {
//                apply("com.android.application")
//                apply("org.jetbrains.kotlin.android")
//            }

            apply(plugin = "com.android.application")
            apply(plugin = "org.jetbrains.kotlin.android")

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(commonExtension = this)
                defaultConfig.targetSdk = 34
            }
        }
    }
}