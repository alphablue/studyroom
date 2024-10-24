package com.example.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        // kotlin 2.0 이후 버전에서는 자동으로 compose compile 이 통합되어 배포 되기 때문에 따로 설정 할 필요가 없다.
        // 업데이트 이후 사용 하지 않도록 할 것
        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("androidxComposeCompiler").get().toString()
        }

        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
            add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
        }

        tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions {
                freeCompilerArgs += buildComposeMetricsParameters()
                freeCompilerArgs += stabilityConfiguration()
            }
        }
    }
}

// 사용하는 이유에 대한 정보
// https://github.com/JetBrains/kotlin/blob/master/plugins/compose/design/compiler-metrics.md
private fun Project.buildComposeMetricsParameters(): List<String> {
    val metricParameters = mutableListOf<String>()
    val enableMetricsProvider = project.providers.gradleProperty("enableComposeCompilerMetrics")
    val relativePath = projectDir.relativeTo(rootDir)
    val buildDir = layout.buildDirectory.get().asFile
    val enableMetrics = (enableMetricsProvider.orNull == "true")

    if (enableMetrics) {
        val metricsFolder = buildDir.resolve("compose-metrics").resolve(relativePath)
        metricParameters.add("-P")
        metricParameters.add(
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${metricsFolder.absolutePath}"
        )

        val enableReportProvider = project.providers.gradleProperty("enableComposeCompilerReports")
        val enableReports = (enableReportProvider.orNull == "true")
        if (enableReports) {
            val reportsFolder = buildDir.resolve("compose-reports").resolve(relativePath)
            metricParameters.add("-P")
            metricParameters.add(
                "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${reportsFolder.absolutePath}"
            )
        }
    }
    return metricParameters.toList()
}

private fun Project.stabilityConfiguration() = listOf(
    "-P",
    "plugin:androidx.compose.compiler.plugins.kotlin:stabilityConfigurationPath=${project.rootDir.absolutePath}/compose_compiler_config.conf"
)
