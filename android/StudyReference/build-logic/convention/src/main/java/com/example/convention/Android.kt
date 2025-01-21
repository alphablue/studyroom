package com.example.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = 35

        defaultConfig {
            minSdk = 26
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }
    }

    configureKotlin<KotlinAndroidProjectExtension>()

    dependencies {
        // desugar 외 빌드의 구조를 이해할 수 있는 자료
        // https://medium.com/mj-studio/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%96%B4%EB%94%94%EA%B9%8C%EC%A7%80-%EC%95%84%EC%84%B8%EC%9A%94-1-build-process-df6a69f73337
        // 라이브러리 깃헙
        // https://github.com/google/desugar_jdk_libs
        add("coreLibraryDesugaring", libs.findLibrary("android.desugarJdkLibs").get())
    }
}

internal fun Project.configureKotlinJVM() {

    // [dsl - 1]
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    configureKotlin<KotlinJvmProjectExtension>()

    /**
     * kotlin 2.0.0 이후 dsl 형태로 바꾸면서 [dsl - 1] 의 방식으로 사용함
     * */
//    tasks.withType<KotlinCompile>().configureEach {
//        kotlinOptions {
//            jvmTarget = JavaVersion.VERSION_17.toString()
//
//            // 아래 옵션에 관한 상세 내용은 링크를 참조 하자
//            // https://kotlinlang.org/docs/gradle-compiler-options.html#common-attributes
//            val warningAsErrors: String? by project
//            allWarningsAsErrors = warningAsErrors.toBoolean()
//            freeCompilerArgs = freeCompilerArgs + listOf(
//                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
//            )
//        }
//    }
}

private inline fun <reified T : KotlinBaseExtension> Project.configureKotlin() = configure<T> {
    val warningsAsErrors: String? by project

    when(this) {
        is KotlinAndroidProjectExtension -> compilerOptions
        is KotlinJvmProjectExtension -> compilerOptions
        else -> TODO()
    }.apply {
        jvmTarget = JvmTarget.JVM_17
        allWarningsAsErrors = warningsAsErrors.toBoolean()
        freeCompilerArgs.add(
            // Enable experimental coroutines APIs, including Flow
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }
}