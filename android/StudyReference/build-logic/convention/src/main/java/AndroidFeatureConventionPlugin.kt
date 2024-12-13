import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("reference.android.library")
                apply("reference.android.hilt")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                add("implementation", project(":studyFeature:fastthirtyfivefinal"))

                add("implementation", libs.findLibrary("androidx.hilt.compose.navigation"))
                add("implementation", libs.findLibrary("androidx.lifecycle.runtime.compose"))
                add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel.compose"))
                add("implementation", libs.findLibrary("androidx.compose-navigation"))
                add("implementation", libs.findLibrary("kotlinx.serialization.json").get())

            }
        }
    }
}