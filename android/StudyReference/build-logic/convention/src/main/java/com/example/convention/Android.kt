package com.example.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = 34

        defaultConfig {
            minSdk = 26
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }
    }

    configureKotlin()

    dependencies {
        // desugar 외 빌드의 구조를 이해할 수 있는 자료
        // https://medium.com/mj-studio/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%96%B4%EB%94%94%EA%B9%8C%EC%A7%80-%EC%95%84%EC%84%B8%EC%9A%94-1-build-process-df6a69f73337
        // 라이브러리 깃헙
        // https://github.com/google/desugar_jdk_libs
        add("coreLibraryDesugaring", libs.findLibrary("android.desugarJdkLibs").get())
    }
}

internal fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()

            // 아래 옵션에 관한 상세 내용은 링크를 참조 하자
            // https://kotlinlang.org/docs/gradle-compiler-options.html#common-attributes
            val warningAsErrors: String? by project
            allWarningsAsErrors = warningAsErrors.toBoolean()
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
            )
        }
    }
}